package am.agrotrade.service;

import am.agrotrade.model.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentService {

    void save(Document document);

    void delete(long chatId);

    List<Document> findAll();

    Optional<Document> findById(long documentId);
}
