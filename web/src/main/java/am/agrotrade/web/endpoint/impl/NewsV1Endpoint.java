package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import am.agrotrade.common.dto.news.response.NewsResponse;
import am.agrotrade.core.service.NewsService;
import am.agrotrade.web.endpoint.NewsV1API;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@RequiredArgsConstructor
public class NewsV1Endpoint implements NewsV1API {

    private final NewsService newsService;

    @Override
    public Page<NewsResponse> getAll(Pageable pageable) {
        return newsService.findAll(pageable)
                .map(NewsResponse::new);
    }

    @Override
    public Page<NewsResponse> getMyNews(long authorId, Pageable pageable) {
        return newsService.findByAuthorId(authorId, pageable)
                .map(NewsResponse::new);
    }

    @Override
    public NewsResponse getById(long id) {
        return new NewsResponse(newsService.findById(id));
    }

    @Override
    public NewsResponse createNews(long authorId, CreateNewsRequest request) {
        return new NewsResponse(newsService.save(authorId, request));
    }

    @Override
    public NewsResponse updateNews(long authorId, long id, CreateNewsRequest request) {
        return new NewsResponse(newsService.updateNews(authorId, id, request));
    }

    @Override
    public void delete(@CurrentUserId long authorId,
                       @PathVariable long id) {
        newsService.delete(authorId, id);
    }
}
