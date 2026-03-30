package am.agrotrade.core.mapper;

import am.agrotrade.common.dto.passport.PassportInfoDto;
import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;
import am.agrotrade.core.model.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PassportMapper {

    @Mapping(target = "userInfoDto", source = "user")
    PassportInfoDto toDto(Passport passport);

    Passport toEntity(CreateAndUpdatePassportRequest passportRequest);

}
