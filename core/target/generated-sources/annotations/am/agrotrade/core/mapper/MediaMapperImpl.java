package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.core.model.Media;
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
public class MediaMapperImpl extends MediaMapper {

    @Override
    public MediaDto toDto(Media media) {
        if ( media == null ) {
            return null;
        }

        Long id = null;
        String fileName = null;
        String fileType = null;
        String subFolder = null;
        Long entityId = null;

        id = media.getId();
        fileName = media.getFileName();
        fileType = media.getFileType();
        subFolder = media.getSubFolder();
        entityId = media.getEntityId();

        String url = MediaDto.buildUrl(baseUrl, media.getSubFolder(), media.getFileName());

        MediaDto mediaDto = new MediaDto( id, fileName, fileType, subFolder, entityId, url );

        return mediaDto;
    }

    @Override
    public List<MediaDto> toDtoList(List<Media> mediaList) {
        if ( mediaList == null ) {
            return null;
        }

        List<MediaDto> list = new ArrayList<MediaDto>( mediaList.size() );
        for ( Media media : mediaList ) {
            list.add( toDto( media ) );
        }

        return list;
    }
}
