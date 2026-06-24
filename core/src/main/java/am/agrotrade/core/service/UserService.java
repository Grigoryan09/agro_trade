package am.agrotrade.core.service;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;

/**
 * Handles current user profile operations.
 */
public interface UserService {

    /**
     * Returns user data by username.
     *
     * @param username username
     * @return user data
     */
    BaseUserInfoDto get(String username);

    /**
     * Updates user profile data.
     *
     * @param username username
     * @param request updated profile payload
     * @return updated user data
     */
    BaseUserInfoDto update(String username, UpdateUserRequest request);

    /**
     * Changes user password.
     *
     * @param username username
     * @param request password change payload
     * @return updated user data
     */
    BaseUserInfoDto changePassword(String username, ChangePasswordRequest request);
}
