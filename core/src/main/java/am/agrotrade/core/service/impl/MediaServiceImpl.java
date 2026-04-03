package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.enums.EntityType;
import am.agrotrade.core.exception.ImageUploadException;
import am.agrotrade.core.exception.InvalidFileException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.MediaMapper;
import am.agrotrade.core.model.Media;
import am.agrotrade.core.repository.MediaRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service implementation for managing media files (product images, user avatars).
 * Handles physical file storage on the disk and metadata persistence in the database.
 */
@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final MediaMapper mediaMapper;

    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * Updates the avatar for a specific user.
     *
     * @param userId the ID of the user.
     * @param file the multipart image file.
     * @return {@link MediaDto} containing the saved file information.
     * @throws ResourceNotFoundException if the user does not exist.
     */
    @Override
    public MediaDto updateAvatar(long userId, MultipartFile file) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return saveMedia(file, userId, EntityType.USER);
    }

    /**
     * Saves a single file to the file system and records its metadata in the database.
     *
     * @param file the file to be uploaded.
     * @param entityId the ID of the related entity (Product ID, User ID, etc.).
     * @param type the type of the entity (USER, PRODUCT).
     * @return {@link MediaDto} of the persisted media record.
     * @throws InvalidFileException if the uploaded file is empty.
     * @throws ImageUploadException if an I/O error occurs during file storage.
     */
    @Override
    public MediaDto saveMedia(MultipartFile file, long entityId, EntityType type) {
        validateFile(file);

        String fileName = generateFileName(file);
        String subFolder = subFolder(type);
        Path filePath = storeFile(file, subFolder, fileName);

        // Fixed: Passing the 'type' parameter to the builder
        Media media = buildMedia(file, fileName, filePath, subFolder, entityId, type);

        return mediaMapper.toDto(mediaRepository.save(media));
    }

    /**
     * Batch saves multiple files for a single entity.
     *
     * @param files list of multipart files.
     * @param entityId the ID of the related entity.
     * @param entityType the type of the entity.
     * @return list of {@link MediaDto} for the saved files.
     */
    @Override
    public List<MediaDto> saveMultipleMedia(List<MultipartFile> files, long entityId, EntityType entityType) {
        if (files == null || files.isEmpty()) {
            return List.of();
        }
        return files.stream()
                .map(file -> saveMedia(file, entityId, entityType))
                .toList();
    }

    /**
     * Loads the most recent media file for an entity as a downloadable resource.
     *
     * @param entityId the ID of the entity.
     * @param entityType the type of the entity.
     * @return {@link Resource} representing the file.
     * @throws ResourceNotFoundException if no media record is found in the database.
     * @throws ImageUploadException if the file is missing from the physical storage.
     */
    @Override
    public Resource loadMediaAsResource(long entityId, EntityType entityType) {
        Media media = mediaRepository.findFirstByEntityIdAndEntityTypeOrderByIdDesc(entityId, entityType)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No media found for %s with ID: %d".formatted(entityType, entityId)
                ));
        try {
            Path filePath = Paths.get(media.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ImageUploadException("File exists in database but is missing on storage");
            }
        } catch (MalformedURLException e) {
            throw new ImageUploadException("Internal error while accessing the file");
        }
    }

    /**
     * Retrieves all media metadata associated with a specific entity.
     *
     * @param entityId the ID of the entity.
     * @param entityType the type of the entity.
     * @return list of {@link MediaDto}.
     */
    @Override
    public List<MediaDto> findAllByEntity(long entityId, EntityType entityType) {
        return mediaMapper.toDtoList(mediaRepository.findAllByEntityIdAndEntityType(entityId, entityType));
    }

    /**
     * Checks if the uploaded file is valid and not empty.
     */
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidFileException("Cannot save empty file");
        }
    }

    /**
     * Generates a unique filename using a UUID to prevent naming collisions.
     */
    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }

    /**
     * Physically stores the file in the designated upload directory.
     * Automatically creates subdirectories based on entity type if they do not exist.
     */
    private Path storeFile(MultipartFile file, String subFolder, String fileName) {
        try {
            Path targetPath = Paths.get(uploadDir, subFolder)
                    .toAbsolutePath()
                    .normalize();

            Files.createDirectories(targetPath);

            Path filePath = targetPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath;
        } catch (IOException e) {
            throw new ImageUploadException("Could not store file. Please try again!");
        }
    }

    /**
     * Builds the Media entity for database persistence.
     */
    private Media buildMedia(MultipartFile file,
                             String fileName,
                             Path filePath,
                             String subFolder,
                             long entityId,
                             EntityType type) { // Added EntityType parameter

        return Media.builder()
                .fileName(fileName)
                .fileType(file.getContentType())
                .filePath(filePath.toString())
                .entityType(type) // Now correctly uses the passed type
                .entityId(entityId)
                .subFolder(subFolder)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * Determines the sub-folder name based on the entity type (e.g., "users", "products").
     */
    private String subFolder(EntityType entityType){
        return entityType.name().toLowerCase() + "s";
    }
}