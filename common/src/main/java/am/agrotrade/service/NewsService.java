package am.agrotrade.service;

import am.agrotrade.model.News;

import java.util.List;
import java.util.Optional;

public interface NewsService {

    void save(News news);

    void delete(long newsId);

    List<News> findAll();

    Optional<News> findById(long newsId);
}
