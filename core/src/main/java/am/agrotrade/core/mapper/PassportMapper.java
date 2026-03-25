package am.agrotrade.core.mapper;

import am.agrotrade.core.model.Passport;
import am.agrotrade.common.dto.passport.request.CreatePassportRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PassportMapper {

    Passport toEntity(CreatePassportRequest passportRequest);

}
