package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.enums.EntityType;
import am.agrotrade.core.exception.ImageUploadException;
import am.agrotrade.core.exception.InvalidFileException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.MediaMapper;
import am.agrotrade.core.model.Media;
import am.agrotrade.core.repository.MediaRepository;
import am.agrotrade.core.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
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

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public MediaDto saveMedia(MultipartFile file, String subFolder, long entityId) {
        if (file.isEmpty()) {
            throw new InvalidFileException("Cannot save empty file");
        }

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path targetPath = Paths.get(uploadDir, subFolder).toAbsolutePath().normalize();
            Files.createDirectories(targetPath);

            Path filePath = targetPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Media media = Media.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .filePath(filePath.toString())
                    .entityType(EntityType.USER)
                    .entityId(entityId)
                    .subFolder(subFolder)
                    .createdAt(LocalDateTime.now())
                    .build();

            return mediaMapper.toDto(mediaRepository.save(media));
        } catch (IOException e) {
            throw new ImageUploadException("Could not store file. Please try again!");
        }
    }

    @Override
    public Resource loadMediaAsResource(long entityId, EntityType entityType) {
        Media media = mediaRepository.findFirstByEntityIdAndEntityTypeOrderByIdDesc(entityId, entityType)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No media found for " + entityType + " with ID: " + entityId));
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

    @Override
    public List<MediaDto> findAllByEntity(long entityId, EntityType entityType) {
        return mediaMapper.toDtoList(mediaRepository.findAllByEntityIdAndEntityType(entityId, entityType));
    }
}
