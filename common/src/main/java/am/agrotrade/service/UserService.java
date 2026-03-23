package am.agrotrade.service;

import am.agrotrade.dto.user.BaseUserInfoDto;
import am.agrotrade.dto.user.request.AuthUserRequest;
import am.agrotrade.dto.user.response.AuthUserDto;

import java.awt.print.Pageable;
import java.util.List;

public interface UserService {

    void save(AuthUserRequest authUserRequest);

    void delete(long userId);

    List<BaseUserInfoDto> findAll(Pageable pageable);

    AuthUserDto findById(long userId);

    long findUserIdByRole(String role);




}
