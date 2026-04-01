package am.agrotrade.web.endpoint.impl;

import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;
import am.agrotrade.common.dto.passport.response.PassportInfoResponse;
import am.agrotrade.core.security.UserPrincipal;
import am.agrotrade.core.service.PassportService;
import am.agrotrade.web.endpoint.PassportV1API;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PassportV1Endpoint implements PassportV1API {

    private final PassportService passportService;

    @Override
    public PassportInfoResponse get(UserPrincipal user) {
        return new PassportInfoResponse(passportService.getPassport(user.getId()));
    }

    @Override
    public PassportInfoResponse create(UserPrincipal user, CreateAndUpdatePassportRequest request) {
        return new PassportInfoResponse(passportService.add(user.getId(),request));
    }

    @Override
    public PassportInfoResponse update(UserPrincipal user, CreateAndUpdatePassportRequest request) {
        return new PassportInfoResponse(passportService.update(user.getId(),request));
    }

    @Override
    public void delete(UserPrincipal user) {
        passportService.delete(user.getId());
    }
}
