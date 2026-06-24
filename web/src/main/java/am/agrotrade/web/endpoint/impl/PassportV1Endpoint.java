package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;
import am.agrotrade.common.dto.passport.response.PassportInfoResponse;
import am.agrotrade.core.security.UserPrincipal;
import am.agrotrade.core.service.PassportService;
import am.agrotrade.web.endpoint.PassportV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PassportV1Endpoint implements PassportV1API {

    private final PassportService passportService;

    @Override
    public PassportInfoResponse get(long userId) {
        return new PassportInfoResponse(List.of(passportService.getPassport(userId)));
    }

    @Override
    public PassportInfoResponse create(long userId, CreateAndUpdatePassportRequest request) {
        return new PassportInfoResponse(List.of(passportService.add(userId, request)));
    }

    @Override
    public PassportInfoResponse update(long userId, CreateAndUpdatePassportRequest request) {
        return new PassportInfoResponse(List.of(passportService.update(userId, request)));
    }

    @Override
    public void delete(long userId) {
        passportService.delete(userId);
    }
}
