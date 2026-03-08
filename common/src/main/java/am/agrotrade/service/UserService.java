package am.agrotrade.service;

import am.agrotrade.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);

    void delete(long userId);

    List<User> findAll();

    Optional<User> findById(long userId);
}
