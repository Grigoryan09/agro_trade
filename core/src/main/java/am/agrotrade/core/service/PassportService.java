package am.agrotrade.core.service;

import am.agrotrade.common.dto.passport.PassportInfoDto;
import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;

public interface PassportService {

    PassportInfoDto getPassport(long userId);

    PassportInfoDto add(long userId , CreateAndUpdatePassportRequest request);

    PassportInfoDto update(long userId, CreateAndUpdatePassportRequest request);

    void delete(long userId);


}
