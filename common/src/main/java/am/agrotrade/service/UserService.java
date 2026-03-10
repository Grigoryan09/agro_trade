package am.agrotrade.service;

import am.agrotrade.dto.user.response.AuthResponse;
import am.agrotrade.dto.user.response.UserResponse;
import am.agrotrade.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void delete(long userId);

    List<UserResponse> findAll();

    AuthResponse findById(long userId);
}
