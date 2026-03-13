package am.agrotrade.dto.passport.request;

import java.time.LocalDate;

public record CreatePassportRequest(

        long userId,
        String passportNumber,
        LocalDate issueDate,
        LocalDate expiryDate,
        String issuedBy

) {
}
