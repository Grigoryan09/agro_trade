package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.passport.PassportInfoDto;
import am.agrotrade.common.dto.passport.request.CreateAndUpdatePassportRequest;
import am.agrotrade.core.exception.AlreadyExistsException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.PassportMapper;
import am.agrotrade.core.model.Passport;
import am.agrotrade.core.model.User;
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
                        -> new ResourceNotFoundException(
                                "Passport not found for user ID: %d".formatted(userId))));
    }

    @Override
    public PassportInfoDto add(long userId, CreateAndUpdatePassportRequest request) {

        if (passportRepository.existsByUserId(userId)) {
            throw new AlreadyExistsException(
                    "Passport already exists for user ID: %d. Use update instead.".formatted(userId)
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found for user ID: %d".formatted(userId)
                ));

        Passport passport = passportMapper.toEntity(request);
        passport.setUser(user);

        return passportMapper.toDto(passportRepository.save(passport));
    }

    @Override
    public PassportInfoDto update(long userId, CreateAndUpdatePassportRequest request) {
        Passport existingPassport = passportRepository.findByUserId(userId)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Cannot update: Passport not found for user ID:"));

        passportMapper.updatePassportFromRequest(request,existingPassport);

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
