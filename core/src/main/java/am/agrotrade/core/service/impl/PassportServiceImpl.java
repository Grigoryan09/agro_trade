package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.passport.PassportInfoDto;
import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;
import am.agrotrade.core.exception.AlreadyExistsException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.exception.UserNotFoundException;
import am.agrotrade.core.mapper.PassportMapper;
import am.agrotrade.core.model.Passport;
import am.agrotrade.core.repository.PassportRepository;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.PassportService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final UserRepository userRepository;
    private final PassportMapper passportMapper;


    @Override
    public PassportInfoDto getPassport(long userId) {
        return passportMapper.toDto(passportRepository.findByUserId(userId)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Passport not found for user ID: " + userId)));
    }

    @Override
    public PassportInfoDto add(long userId, CreateAndUpdatePassportRequest request) {
        if (passportRepository.existsByUserId(userId)) {
            throw new AlreadyExistsException("Passport already exists for this user. Use update instead.");
        }
        Passport passport = passportMapper.toEntity(request);
        passport.setUser(userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("User not found for user ID")));

        return passportMapper.toDto(passportRepository.save(passport));
    }

    @Override
    public PassportInfoDto update(long userId, CreateAndUpdatePassportRequest request) {
        Passport existingPassport = passportRepository.findByUserId(userId)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Cannot update: Passport not found for user ID:"));
        existingPassport.setPassportNumber(request.passportNumber());
        existingPassport.setIssueDate(request.issueDate());
        existingPassport.setExpiryDate(request.expiryDate());
        existingPassport.setIssuedBy(request.issuedBy());

        return passportMapper.toDto(passportRepository.save(existingPassport));
    }

    @Override
    @Transactional
    public void delete(long userId) {
        if (!passportRepository.existsByUserId(userId)) {
            throw new ResourceNotFoundException("Cannot delete: Passport not found for user ID");
        }
        passportRepository.deleteByUserId(userId);
    }
}
