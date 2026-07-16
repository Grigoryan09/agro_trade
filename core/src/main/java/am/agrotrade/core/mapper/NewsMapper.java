package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.news.request.CreateNewsRequest;
import am.agrotrade.common.dto.news.BaseNewsInfoDto;
import am.agrotrade.core.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    News toEntity(CreateNewsRequest request);

    @Mapping(target = "user", source = "author")
    @Mapping(target = "media", ignore = true)
    BaseNewsInfoDto toDto(News news);

    List<BaseNewsInfoDto> toDtoList(List<News> newsList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateNewsFromRequest(CreateNewsRequest request, @MappingTarget News news);
}
