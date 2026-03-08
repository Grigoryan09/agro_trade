package am.agrotrade.service;

import am.agrotrade.model.Passport;

import java.util.List;
import java.util.Optional;

public interface PassportService {

    void save(Passport passport);

    void delete(long passportId);

    List<Passport> findAll();

    Optional<Passport> findById(long passportId);
}
