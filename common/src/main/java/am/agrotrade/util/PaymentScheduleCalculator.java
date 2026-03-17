package am.agrotrade.util;


import am.agrotrade.dto.PaymentRowDto;
import am.agrotrade.dto.request.DocumentGenerateRequest;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class PaymentScheduleCalculator {

    public static List<PaymentRowDto> calculateSchedule(DocumentGenerateRequest request) {

        BigDecimal monthlyRate = request.offerDto().interestRate()
                .divide(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(12), MathContext.DECIMAL64);

        BigDecimal monthlyPayment = calculateMonthlyPayment(request.finalContractDto().approvedAmount(), request.offerDto().interestRate(), request.finalContractDto().approvedPeriod());

        BigDecimal balance = request.finalContractDto().approvedAmount();
        List<PaymentRowDto> schedule = new ArrayList<>();

        for (int month = 1; month <= request.finalContractDto().approvedPeriod(); month++) {

            BigDecimal interest = balance.multiply(monthlyRate);

            BigDecimal principal = monthlyPayment.subtract(interest);

            balance = balance.subtract(principal);

            schedule.add(new PaymentRowDto(
                    month,
                    monthlyPayment,
                    interest,
                    principal,
                    balance
            ));
        }

        return schedule;
    }

    public static BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal yearlyRate, int months) {


        BigDecimal monthlyRate = yearlyRate
                .divide(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(12), MathContext.DECIMAL64);

        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate);
        BigDecimal power = onePlusR.pow(months);

        BigDecimal numerator = amount.multiply(monthlyRate).multiply(power);
        BigDecimal denominator = power.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, MathContext.DECIMAL64);
    }
}
