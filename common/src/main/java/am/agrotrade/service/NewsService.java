package am.agrotrade.service;

import am.agrotrade.dto.news.request.CreateNewsRequest;
import am.agrotrade.dto.news.response.BaseNewsInfoDto;

import java.awt.print.Pageable;
import java.util.List;

public interface NewsService {

    void save(CreateNewsRequest news);

    void delete(long newsId);

    List<BaseNewsInfoDto> findAll(Pageable pageable);

    BaseNewsInfoDto findById(long newsId);

    List<BaseNewsInfoDto> findByAuthorId(long authorId, Pageable pageable);
}
