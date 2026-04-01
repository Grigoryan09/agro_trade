package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.media.response.MediaResponse;
import am.agrotrade.core.security.UserPrincipal;
import am.agrotrade.core.service.MediaService;
import am.agrotrade.web.endpoint.MediaV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class MediaV1Endpoint implements MediaV1API {

    private final MediaService mediaService;

    @Override
    public MediaResponse uploadAvatar(UserPrincipal user, MultipartFile file) {
        return new MediaResponse(mediaService.updateAvatar(user.getId(),file));
    }
}
