package am.agrotrade.service;

import am.agrotrade.dto.passport.request.CreatePassportRequest;

public interface PassportService {

    void save(CreatePassportRequest createPassportRequest);

    boolean existsPassportByUserId(long userId);

}
