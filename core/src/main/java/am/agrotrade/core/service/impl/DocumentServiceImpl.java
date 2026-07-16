package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.document.request.CreateDocumentDto;
import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.core.exception.ImageUploadException;
import am.agrotrade.core.model.Document;
import am.agrotrade.core.repository.DocumentRepository;
import am.agrotrade.core.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private static final String SUB_FOLDER = "documents";

    private final DocumentRepository documentRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public String save(CreateDocumentDto document) {
        String storedName = UUID.randomUUID() + "_" + document.name();
        Path filePath = writeToDisk(document.base64Content(), storedName);

        documentRepository.save(new Document(
                0,
                document.name(),
                document.type(),
                document.format(),
                filePath.toString(),
                document.createdAt() != null ? document.createdAt() : LocalDateTime.now()
        ));

        return MediaDto.buildUrl(baseUrl, SUB_FOLDER, storedName);
    }

    private Path writeToDisk(String base64Content, String storedName) {
        byte[] content = Base64.getDecoder().decode(base64Content);
        try {
            Path directory = Paths.get(uploadDir, SUB_FOLDER).toAbsolutePath().normalize();
            Files.createDirectories(directory);
            Path filePath = directory.resolve(storedName);
            Files.write(filePath, content);
            return filePath;
        } catch (IOException e) {
            throw new ImageUploadException("Could not store generated document. Please try again!");
        }
    }
}
