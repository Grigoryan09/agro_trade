package am.agrotrade.service;

import am.agrotrade.dto.document.request.CreateDocumentRequest;

public interface DocumentService {

    void save(CreateDocumentRequest document);
}
