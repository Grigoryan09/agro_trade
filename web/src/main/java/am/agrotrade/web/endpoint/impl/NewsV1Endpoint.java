package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import am.agrotrade.common.dto.news.response.NewsResponse;
import am.agrotrade.core.service.NewsService;
import am.agrotrade.web.endpoint.NewsV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsV1Endpoint implements NewsV1API {

    private final NewsService newsService;

    @Override
    public NewsResponse getAll(Pageable pageable) {
        return new NewsResponse(newsService.findAll(pageable));
    }

    @Override
    public NewsResponse getMyNews(long authorId, Pageable pageable) {
        return new NewsResponse(newsService.findByAuthorId(authorId, pageable));
    }

    @Override
    public NewsResponse getById(long id) {
        return new NewsResponse(List.of(newsService.findById(id)));
    }

    @Override
    public NewsResponse createNews(long authorId, CreateNewsRequest request) {
        return new NewsResponse(List.of(newsService.save(authorId, request)));
    }

    @Override
    public NewsResponse updateNews(long id, CreateNewsRequest request) {
        return new NewsResponse(List.of(newsService.updateNews(id, request)));
    }

    @Override
    public void delete(long id) {
        newsService.delete(id);
    }
}
