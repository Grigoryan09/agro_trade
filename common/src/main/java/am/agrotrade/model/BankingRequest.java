package am.agrotrade.model;

import am.agrotrade.model.enums.BankingRequestStatus;
import am.agrotrade.model.enums.BankingRequestType;
import am.agrotrade.model.enums.RepaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banking_request")
public class BankingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private BankingRequestType type;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String purpose;

    private BigDecimal creditAmount;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private BankingRequestStatus requestStatus;

    private int repaymentPeriod;

    @Enumerated(EnumType.STRING)
    private RepaymentType  repaymentType;


}
