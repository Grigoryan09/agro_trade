package am.agrotrade.common.dto.passport.request;

import java.time.LocalDate;

public record CreatePassportRequest(

        String passportNumber,
        LocalDate issueDate,
        LocalDate expiryDate,
        String issuedBy

) {
}
