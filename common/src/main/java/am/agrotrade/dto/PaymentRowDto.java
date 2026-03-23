package am.agrotrade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PaymentRowDto {

    private int month;
    private BigDecimal monthlyPayment;
    private BigDecimal interest;
    private BigDecimal principal;
    private BigDecimal balance;

}
