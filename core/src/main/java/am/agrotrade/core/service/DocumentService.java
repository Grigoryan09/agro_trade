package am.agrotrade.core.service;

import am.agrotrade.common.dto.document.request.CreateDocumentDto;

public interface DocumentService {

    void save(CreateDocumentDto document);
}
