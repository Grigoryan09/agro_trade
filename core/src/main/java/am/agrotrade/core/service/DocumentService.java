package am.agrotrade.core.service;

import am.agrotrade.common.dto.document.request.CreateDocumentDto;

/**
 * Handles document creation operations.
 */
public interface DocumentService {

    /**
     * Saves a document.
     *
     * @param document document payload
     */
    void save(CreateDocumentDto document);
}
