package am.agrotrade.core.service;

import am.agrotrade.common.dto.news.BaseNewsInfoDto;
import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing news in AgroTrade application.
 *
 * <p>Provides business logic for news CRUD operations, including creation,
 * updating, deletion and various search methods with pagination support.
 */
public interface NewsService {

    /**
     * Creates a new news for the specified author.
     *
     * @param authorId ID of the author who creates the news
     * @param request  request containing news details (title and context)
     * @return {@link BaseNewsInfoDto} of the created news
     */
    BaseNewsInfoDto save(long authorId, CreateNewsRequest request);

    /**
     * Updates an existing news of the specified author.
     *
     * @param authorId ID of the author who owns the news
     * @param newsId   ID of the news to update
     * @param request  request containing updated news data
     * @return {@link BaseNewsInfoDto} of the updated news
     */
    BaseNewsInfoDto updateNews(long authorId, long newsId, CreateNewsRequest request);

    /**
     * Deletes a news item for the specified author.
     *
     * @param userId ID of the user who owns the news
     * @param newsId ID of the news to delete
     */
    void delete(long userId, long newsId);

    /**
     * Retrieves all news with pagination.
     *
     * @param pageable pagination and sorting parameters
     * @return paginated list of {@link BaseNewsInfoDto}
     */
    Page<BaseNewsInfoDto> findAll(Pageable pageable);

    /**
     * Retrieves a specific news by its ID.
     *
     * @param newsId ID of the news
     * @return {@link BaseNewsInfoDto} containing news details
     */
    BaseNewsInfoDto findById(long newsId);

    /**
     * Retrieves all news belonging to a specific author with pagination.
     *
     * @param authorId ID of the author
     * @param pageable pagination and sorting parameters
     * @return paginated list of {@link BaseNewsInfoDto}
     */
    Page<BaseNewsInfoDto> findByAuthorId(long authorId, Pageable pageable);
}
