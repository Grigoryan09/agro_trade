package am.agrotrade.core.service;

import am.agrotrade.common.dto.passport.request.CreatePassportRequest;

public interface PassportService {

    void save(CreatePassportRequest createPassportRequest);

    boolean existsPassportByUserId(long userId);

}
