package am.agrotrade.service;

import am.agrotrade.dto.user.response.AuthResponse;
import am.agrotrade.dto.user.response.UserResponse;
import am.agrotrade.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);

    void delete(long userId);

    List<UserResponse> findAll();

    Optional<AuthResponse> findById(long userId);
}
