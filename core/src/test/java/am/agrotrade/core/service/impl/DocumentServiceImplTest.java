package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.document.request.CreateDocumentDto;
import am.agrotrade.common.enums.DocumentFormat;
import am.agrotrade.common.enums.DocumentType;
import am.agrotrade.core.exception.ImageUploadException;
import am.agrotrade.core.model.Document;
import am.agrotrade.core.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(documentService, "uploadDir", tempDir.toString());
        ReflectionTestUtils.setField(documentService, "baseUrl", "http://localhost:8080");
    }

    private CreateDocumentDto document(String base64) {
        return new CreateDocumentDto(DocumentType.ORDER, "contract.docx", DocumentFormat.DOCX,
                null, LocalDateTime.now(), base64);
    }

    @Test
    void save_writesFileToDisk_persistsDocument_andReturnsUrl() throws Exception {
        String content = "hello-document";
        String base64 = Base64.getEncoder().encodeToString(content.getBytes());

        String url = documentService.save(document(base64));

        assertThat(url).startsWith("http://localhost:8080/media/documents/");
        assertThat(url).endsWith("_contract.docx");

        ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);
        verify(documentRepository).save(captor.capture());
        Path stored = Path.of(captor.getValue().getFilePath());
        assertThat(Files.exists(stored)).isTrue();
        assertThat(Files.readString(stored)).isEqualTo(content);
    }

    @Test
    void save_invalidBase64_throws() {
        assertThatThrownBy(() -> documentService.save(document("not-valid-base64!!!")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
