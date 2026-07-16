package am.agrotrade.core.repository;

import am.agrotrade.core.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

    Page<News> findByAuthorId(Long authorId, Pageable pageable);
}
