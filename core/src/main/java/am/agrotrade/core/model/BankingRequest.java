package am.agrotrade.core.model;

import am.agrotrade.common.enums.BankingRequestStatus;
import am.agrotrade.common.enums.BankingRequestType;
import am.agrotrade.common.enums.RepaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @ManyToOne
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
    private RepaymentType repaymentType;

}
