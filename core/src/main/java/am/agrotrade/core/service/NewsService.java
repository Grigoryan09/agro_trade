package am.agrotrade.core.service;

import am.agrotrade.common.dto.news.BaseNewsInfoDto;
import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Manages news creation, update, lookup, and removal.
 */
public interface NewsService {

    /**
     * Creates a news item for the specified author.
     *
     * @param authorId author identifier
     * @param request news payload
     * @return created news data
     */
    BaseNewsInfoDto save(long authorId, CreateNewsRequest request);

    /**
     * Updates an existing news item, regardless of its author.
     *
     * @param newsId news identifier
     * @param request updated news payload
     * @return updated news data
     */
    BaseNewsInfoDto updateNews(long newsId, CreateNewsRequest request);

    /**
     * Deletes a news item, regardless of its author.
     *
     * @param newsId news identifier
     */
    void delete(long newsId);

    /**
     * Returns all news entries using the provided paging settings.
     *
     * @param pageable paging parameters
     * @return news list
     */
    List<BaseNewsInfoDto> findAll(Pageable pageable);

    /**
     * Returns a news item by its identifier.
     *
     * @param newsId news identifier
     * @return news data
     */
    BaseNewsInfoDto findById(long newsId);

    /**
     * Returns all news entries created by the specified author.
     *
     * @param authorId author identifier
     * @param pageable paging parameters
     * @return author news list
     */
    List<BaseNewsInfoDto> findByAuthorId(long authorId, Pageable pageable);
}
