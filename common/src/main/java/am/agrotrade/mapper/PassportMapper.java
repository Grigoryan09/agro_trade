package am.agrotrade.mapper;

import am.agrotrade.dto.passport.request.CreatePassportRequest;
import am.agrotrade.model.entity.Passport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    Passport toEntity(CreatePassportRequest passportRequest);

}
