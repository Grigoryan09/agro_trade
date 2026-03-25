package am.agrotrade.core.repository;


import am.agrotrade.core.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
