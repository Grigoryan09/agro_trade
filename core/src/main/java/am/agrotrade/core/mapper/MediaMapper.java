package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.core.model.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MediaMapper {

    @Value("${app.base-url:http://localhost:8080}")
    protected String baseUrl;

    @Mapping(target = "url", expression = "java(MediaDto.buildUrl(baseUrl, media.getSubFolder(), media.getFileName()))")
    public abstract MediaDto toDto(Media media);

    public abstract List<MediaDto> toDtoList(List<Media> mediaList);

}
