package am.agrotrade.service;

import am.agrotrade.dto.document.request.CreateDocumentDto;

public interface DocumentService {

    void save(CreateDocumentDto document);
}
