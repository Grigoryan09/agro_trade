package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.news.BaseNewsInfoDto;
import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import am.agrotrade.common.enums.EntityType;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.NewsMapper;
import am.agrotrade.core.model.News;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.NewsRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.MediaService;
import am.agrotrade.core.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final NewsMapper newsMapper;
    private final MediaService mediaService;

    /**
     * Maps a news entity to its DTO and attaches the media stored for it, so
     * responses carry both the news {@code id} and its image URLs.
     */
    private BaseNewsInfoDto toDtoWithMedia(News news) {
        return newsMapper.toDto(news)
                .withMedia(mediaService.findAllByEntity(news.getId(), EntityType.NEWS));
    }

    @Override
    @Transactional
    public BaseNewsInfoDto save(long authorId, CreateNewsRequest request) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        News news = newsMapper.toEntity(request);
        news.setAuthor(author);
        news.setCreatedAt(LocalDateTime.now());
        News savedNews = newsRepository.save(news);
        return toDtoWithMedia(savedNews);
    }

    @Override
    @Transactional
    public BaseNewsInfoDto updateNews(long newsId, CreateNewsRequest request) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "News not found with id: %s".formatted(newsId)
                ));
        newsMapper.updateNewsFromRequest(request, news);
        News updatedNews = newsRepository.save(news);
        return toDtoWithMedia(updatedNews);
    }

    @Override
    @Transactional
    public void delete(long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new ResourceNotFoundException(
                    "News not found with id: %s".formatted(newsId)
            );
        }
        newsRepository.deleteById(newsId);
    }

    @Override
    @Transactional
    public List<BaseNewsInfoDto> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(this::toDtoWithMedia)
                .getContent();
    }

    @Override
    @Transactional
    public BaseNewsInfoDto findById(long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "News not found with id: %s".formatted(newsId)
                ));
        return toDtoWithMedia(news);
    }

    @Override
    @Transactional
    public List<BaseNewsInfoDto> findByAuthorId(long authorId, Pageable pageable) {
        return newsRepository.findByAuthorId(authorId, pageable)
                .map(this::toDtoWithMedia)
                .getContent();
    }
}


