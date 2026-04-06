package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.news.BaseNewsInfoDto;
import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.NewsMapper;
import am.agrotrade.core.model.News;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.NewsRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final NewsMapper newsMapper;

    @Override
    @Transactional
    public BaseNewsInfoDto save(long authorId, CreateNewsRequest request) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        News news = newsMapper.toEntity(request);
        news.setAuthor(author);
        news.setCreatedAt(LocalDateTime.now());
        News savedNews = newsRepository.save(news);
        return newsMapper.toDto(savedNews);
    }

    @Override
    @Transactional
    public BaseNewsInfoDto updateNews(long authorId, long newsId, CreateNewsRequest request) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResolutionException(
                        "News not found with id: %s".formatted(newsId)
                ));
        if (news.getAuthor() == null || !Objects.equals(news.getAuthor().getId(), authorId)) {
            throw new RuntimeException("You don't have permission to update this news");
        }
        newsMapper.updateNewsFromRequest(request, news);
        News updatedNews = newsRepository.save(news);
        return newsMapper.toDto(updatedNews);
    }

    @Override
    @Transactional
    public void delete(long userId, long newsId) {
        if (!newsRepository.existsByIdAndAuthorId(newsId, userId)) {
            throw new RuntimeException("News not found or you don't have permission to delete it");
        }
        newsRepository.deleteByIdAndAuthorId(newsId, userId);
    }

    @Override
    @Transactional
    public Page<BaseNewsInfoDto> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable).
                map(newsMapper::toDto);
    }

    @Override
    @Transactional
    public BaseNewsInfoDto findById(long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResolutionException(
                        "News not found with id: %s".formatted(newsId)
                ));
        return newsMapper.toDto(news);
    }

    @Override
    @Transactional
    public Page<BaseNewsInfoDto> findByAuthorId(long authorId, Pageable pageable) {
        return newsRepository.findByAuthorId(authorId, pageable)
                .map(newsMapper::toDto);
    }
}


