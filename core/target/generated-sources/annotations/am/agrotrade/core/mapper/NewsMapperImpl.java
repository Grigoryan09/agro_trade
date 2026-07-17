package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.dto.news.BaseNewsInfoDto;
import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.core.model.News;
import am.agrotrade.core.model.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T01:20:33+0400",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.11.0.11 (Alibaba)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public News toEntity(CreateNewsRequest request) {
        if ( request == null ) {
            return null;
        }

        News news = new News();

        news.setTitle( request.title() );
        news.setContext( request.context() );

        return news;
    }

    @Override
    public BaseNewsInfoDto toDto(News news) {
        if ( news == null ) {
            return null;
        }

        BaseUserInfoDto user = null;
        Long id = null;
        String title = null;
        String context = null;
        LocalDateTime createdAt = null;

        user = userToBaseUserInfoDto( news.getAuthor() );
        id = news.getId();
        title = news.getTitle();
        context = news.getContext();
        createdAt = news.getCreatedAt();

        List<MediaDto> media = null;

        BaseNewsInfoDto baseNewsInfoDto = new BaseNewsInfoDto( id, title, context, user, createdAt, media );

        return baseNewsInfoDto;
    }

    @Override
    public List<BaseNewsInfoDto> toDtoList(List<News> newsList) {
        if ( newsList == null ) {
            return null;
        }

        List<BaseNewsInfoDto> list = new ArrayList<BaseNewsInfoDto>( newsList.size() );
        for ( News news : newsList ) {
            list.add( toDto( news ) );
        }

        return list;
    }

    @Override
    public void updateNewsFromRequest(CreateNewsRequest request, News news) {
        if ( request == null ) {
            return;
        }

        news.setTitle( request.title() );
        news.setContext( request.context() );
    }

    protected BaseUserInfoDto userToBaseUserInfoDto(User user) {
        if ( user == null ) {
            return null;
        }

        BaseUserInfoDto baseUserInfoDto = new BaseUserInfoDto();

        baseUserInfoDto.setName( user.getName() );
        baseUserInfoDto.setSurname( user.getSurname() );
        baseUserInfoDto.setGender( user.getGender() );
        baseUserInfoDto.setBirthDate( user.getBirthDate() );
        baseUserInfoDto.setAddress( user.getAddress() );
        baseUserInfoDto.setEmail( user.getEmail() );
        baseUserInfoDto.setPhoneNumber( user.getPhoneNumber() );
        baseUserInfoDto.setUsername( user.getUsername() );

        return baseUserInfoDto;
    }
}
