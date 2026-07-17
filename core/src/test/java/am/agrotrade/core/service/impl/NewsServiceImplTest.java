package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.media.MediaDto;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    private static final long AUTHOR_ID = 5L;
    private static final long NEWS_ID = 11L;

    @Mock
    private NewsRepository newsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private NewsMapper newsMapper;
    @Mock
    private MediaService mediaService;

    @InjectMocks
    private NewsServiceImpl newsService;

    private BaseNewsInfoDto dto;
    private List<MediaDto> media;

    @BeforeEach
    void setUp() {
        dto = new BaseNewsInfoDto(NEWS_ID, "Title", "Context", null, LocalDateTime.now(), null);
        media = List.of(new MediaDto(1L, "n.png", "image/png", "news", NEWS_ID, "/media/1"));
    }

    @Test
    void save_setsAuthorAndCreatedAt_andReturnsDtoWithMedia() {
        CreateNewsRequest request = new CreateNewsRequest("Title", "Some long context");
        User author = new User();
        author.setId(AUTHOR_ID);
        News mapped = new News();
        News saved = new News();
        saved.setId(NEWS_ID);

        when(userRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(author));
        when(newsMapper.toEntity(request)).thenReturn(mapped);
        when(newsRepository.save(mapped)).thenReturn(saved);
        when(newsMapper.toDto(saved)).thenReturn(dto);
        when(mediaService.findAllByEntity(NEWS_ID, EntityType.NEWS)).thenReturn(media);

        BaseNewsInfoDto result = newsService.save(AUTHOR_ID, request);

        assertThat(result.media()).isEqualTo(media);
        assertThat(mapped.getAuthor()).isSameAs(author);
        assertThat(mapped.getCreatedAt()).isNotNull();
    }

    @Test
    void save_authorNotFound_throws() {
        when(userRepository.findById(AUTHOR_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsService.save(AUTHOR_ID, new CreateNewsRequest("t", "ctx")))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(newsRepository, never()).save(any());
    }

    @Test
    void updateNews_appliesMapperAndPersists() {
        CreateNewsRequest request = new CreateNewsRequest("New", "New long context");
        News news = new News();
        news.setId(NEWS_ID);

        when(newsRepository.findById(NEWS_ID)).thenReturn(Optional.of(news));
        when(newsRepository.save(news)).thenReturn(news);
        when(newsMapper.toDto(news)).thenReturn(dto);
        when(mediaService.findAllByEntity(NEWS_ID, EntityType.NEWS)).thenReturn(media);

        BaseNewsInfoDto result = newsService.updateNews(NEWS_ID, request);

        assertThat(result.media()).isEqualTo(media);
        verify(newsMapper).updateNewsFromRequest(request, news);
    }

    @Test
    void updateNews_notFound_throws() {
        when(newsRepository.findById(NEWS_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsService.updateNews(NEWS_ID, new CreateNewsRequest("t", "ctx")))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_existing_deletesById() {
        when(newsRepository.existsById(NEWS_ID)).thenReturn(true);

        newsService.delete(NEWS_ID);

        verify(newsRepository).deleteById(NEWS_ID);
    }

    @Test
    void delete_missing_throws_andDoesNotDelete() {
        when(newsRepository.existsById(NEWS_ID)).thenReturn(false);

        assertThatThrownBy(() -> newsService.delete(NEWS_ID))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(newsRepository, never()).deleteById(NEWS_ID);
    }

    @Test
    void findById_notFound_throws() {
        when(newsRepository.findById(NEWS_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> newsService.findById(NEWS_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
