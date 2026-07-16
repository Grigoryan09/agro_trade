package am.agrotrade.core.service;

import am.agrotrade.common.dto.document.request.CreateDocumentDto;

/**
 * Handles document creation operations.
 */
public interface DocumentService {

    /**
     * Persists a document: writes its Base64 content to disk, stores the
     * {@code Document} metadata and returns a public download URL.
     * Generic for any document type (contract, order, ...).
     *
     * @param document document payload (including Base64 content)
     * @return public download URL of the stored document
     */
    String save(CreateDocumentDto document);
}
