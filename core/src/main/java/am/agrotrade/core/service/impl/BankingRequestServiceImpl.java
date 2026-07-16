package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.PaymentRowDto;
import am.agrotrade.common.dto.banking.event.ContractCreateRequestEvent;
import am.agrotrade.common.dto.banking.event.ContractCreateResultEvent;
import am.agrotrade.common.dto.bankingRequest.request.CreateBankingRequest;
import am.agrotrade.common.dto.bankingRequest.response.BankingRequestInfoDto;
import am.agrotrade.common.dto.document.contract.BankInfoDto;
import am.agrotrade.common.dto.document.contract.ContractClientDto;
import am.agrotrade.common.dto.document.contract.ContractDocumentGenerateEvent;
import am.agrotrade.common.dto.document.contract.ContractFinalDto;
import am.agrotrade.common.dto.document.contract.ContractPassportDto;
import am.agrotrade.common.dto.document.contract.ContractProductDto;
import am.agrotrade.common.dto.document.contract.OfferInfoDto;
import am.agrotrade.common.enums.BankingRequestStatus;
import am.agrotrade.common.enums.DocumentType;
import am.agrotrade.common.enums.RepaymentType;
import am.agrotrade.common.enums.TermUnit;
import am.agrotrade.common.util.PaymentScheduleCalculator;
import am.agrotrade.core.exception.PassportRequiredException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.model.BankingRequest;
import am.agrotrade.core.model.Passport;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.BankingRequestRepository;
import am.agrotrade.core.repository.PassportRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.BankingRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankingRequestServiceImpl implements BankingRequestService {

    private final BankingRequestRepository bankingRequestRepository;
    private final UserRepository userRepository;
    private final PassportRepository passportRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public BankingRequestInfoDto create(long userId, CreateBankingRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for id: %d".formatted(userId)));

        if (!passportRepository.existsByUserId(userId)) {
            throw new PassportRequiredException("Passport data is required to request a credit");
        }

        BankingRequest entity = new BankingRequest();
        entity.setType(request.bankingRequestType());
        entity.setUser(user);
        entity.setPurpose(request.purpose());
        entity.setCreditAmount(request.approvedAmount());
        entity.setRequestStatus(BankingRequestStatus.PENDING);
        entity.setRepaymentPeriod(request.tenor());
        entity.setRepaymentType(RepaymentType.MONTHLY);

        BankingRequest saved = bankingRequestRepository.save(entity);

        eventPublisher.publishEvent(new ContractCreateRequestEvent(
                saved.getId(),
                request.offerId(),
                request.approvedAmount(),
                request.tenor(),
                request.termUnit().name(),
                request.productName(),
                request.productType(),
                user.getPhoneNumber()
        ));

        return toInfo(saved);
    }

    @Override
    @Transactional
    public ContractDocumentGenerateEvent onContractCreated(ContractCreateResultEvent event) {
        BankingRequest request = bankingRequestRepository.findById(event.externalRequestId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Banking request not found for id: %d".formatted(event.externalRequestId())));

        request.setRequestStatus(BankingRequestStatus.APPROVED);

        User user = request.getUser();
        Passport passport = passportRepository.findByUserId(user.getId())
                .orElseThrow(() -> new PassportRequiredException(
                        "Passport data is required to generate a contract"));

        int months = TermUnit.YEARS.name().equalsIgnoreCase(event.tenorUnit())
                ? event.tenor() * 12
                : event.tenor();

        List<PaymentRowDto> schedule = PaymentScheduleCalculator.calculateSchedule(
                event.approvedAmount(), event.interestRate(), months);

        BankInfoDto bank = new BankInfoDto(
                event.bankName(), event.bankPhoneNumber(), event.bankLicenseNumber());

        OfferInfoDto offer = new OfferInfoDto(
                bank, event.offerType(), event.interestRate(),
                event.maxDurationMonths(), event.minAmount());

        ContractFinalDto finalContract = new ContractFinalDto(
                event.approvedAmount(),
                months,
                new ContractProductDto(event.productName(), event.productType()),
                java.time.LocalDateTime.now());

        ContractClientDto client = new ContractClientDto(
                fullName(user),
                user.getEmail(),
                user.getPhoneNumber(),
                new ContractPassportDto(passport.getPassportNumber()));

        return new ContractDocumentGenerateEvent(
                event.externalRequestId(),
                event.finalContractId(),
                bank,
                offer,
                finalContract,
                client,
                schedule,
                DocumentType.CONTRACT.name());
    }

    @Override
    @Transactional
    public void completeContract(long bankingRequestId) {
        BankingRequest request = bankingRequestRepository.findById(bankingRequestId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Banking request not found for id: %d".formatted(bankingRequestId)));
        request.setRequestStatus(BankingRequestStatus.COMPLETED);
    }

    @Override
    @Transactional
    public void delete(long bankingRequestId) {
        if (!bankingRequestRepository.existsById(bankingRequestId)) {
            throw new ResourceNotFoundException(
                    "Banking request not found for id: %d".formatted(bankingRequestId));
        }
        bankingRequestRepository.deleteById(bankingRequestId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankingRequestInfoDto> findByUserId(long userId, Pageable pageable) {
        return bankingRequestRepository.findByUserId(userId, pageable)
                .stream()
                .map(this::toInfo)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BankingRequestInfoDto findById(long bankingRequestId) {
        return bankingRequestRepository.findById(bankingRequestId)
                .map(this::toInfo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Banking request not found for id: %d".formatted(bankingRequestId)));
    }

    private BankingRequestInfoDto toInfo(BankingRequest request) {
        return new BankingRequestInfoDto(
                request.getId(),
                request.getType() != null ? request.getType().name() : null,
                request.getRequestStatus() != null ? request.getRequestStatus().name() : null,
                request.getCreditAmount(),
                request.getRepaymentPeriod());
    }

    private String fullName(User user) {
        return "%s %s".formatted(safe(user.getName()), safe(user.getSurname())).trim();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
