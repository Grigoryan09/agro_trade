package am.agrotrade.web.endpoint;

import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import am.agrotrade.common.dto.news.response.NewsResponse;
import am.agrotrade.web.infrastructure.annotation.CurrentUserId;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API v1 for managing news in AgroTrade service.
 *
 * <p>Provides endpoints for news listing, retrieval, creation and deletion.
 */
@RestController
@RequestMapping("/agro-trade-service/api/v1/news")
public interface NewsV1API {

    /**
     * Retrieves a paginated list of all news.
     *
     * @param pageable pagination and sorting parameters (page, size, sort)
     * @return page of {@link NewsResponse}
     */
    @GetMapping
    Page<NewsResponse> getAll(Pageable pageable);

    /**
     * Retrieves a paginated list of news belonging to the currently authenticated user.
     *
     * @param authorId ID of the currently authenticated user
     *                 (automatically resolved via {@link CurrentUserId})
     * @param pageable pagination and sorting parameters
     * @return page of {@link NewsResponse} for the user's news
     */
    @GetMapping("/my")
    Page<NewsResponse> getMyNews(
            @CurrentUserId long authorId,
            Pageable pageable
    );

    /**
     * Retrieves detailed information about a specific news by its ID.
     *
     * @param id the ID of the news
     * @return {@link NewsResponse} containing news details
     */
    @GetMapping("/{id}")
    NewsResponse getById(@PathVariable long id);

    /**
     * Creates a new news for the currently authenticated user.
     *
     * @param authorId          ID of the currently authenticated user
     * @param createNewsRequest request body containing news creation data
     * @return the created {@link NewsResponse}
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    NewsResponse createNews(
            @CurrentUserId long authorId,
            @Valid @RequestBody CreateNewsRequest createNewsRequest
    );

    /**
     * Updates an existing news of the currently authenticated user.
     *
     * @param authorId ID of the currently authenticated user
     * @param id       ID of the news to update
     * @param request  request body containing updated news data
     * @return the updated {@link NewsResponse}
     */
    @PutMapping("/{id}")
    NewsResponse updateNews(
            @CurrentUserId long authorId,
            @PathVariable long id,
            @Valid @RequestBody CreateNewsRequest request
    );

    /**
     * Deletes a news belonging to the currently authenticated user.
     *
     * @param authorId ID of the currently authenticated user
     * @param id       ID of the news to delete
     */
    @DeleteMapping("/{id}")
    void delete(
            @CurrentUserId long authorId,
            @PathVariable long id
    );
}
