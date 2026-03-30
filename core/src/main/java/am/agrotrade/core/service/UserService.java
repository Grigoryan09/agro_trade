package am.agrotrade.core.service;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    BaseUserInfoDto get(String username);

    BaseUserInfoDto update(String username , UpdateUserRequest request);

    BaseUserInfoDto changePassword(String username, ChangePasswordRequest request);

    MediaDto updateAvatar(long userId, MultipartFile file);
}
