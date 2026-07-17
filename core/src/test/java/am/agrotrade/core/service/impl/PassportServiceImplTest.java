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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassportServiceImplTest {

    private static final long USER_ID = 1L;

    @Mock
    private PassportRepository passportRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PassportMapper passportMapper;

    @InjectMocks
    private PassportServiceImpl passportService;

    private CreateAndUpdatePassportRequest request() {
        return new CreateAndUpdatePassportRequest("AB123456", LocalDate.now().minusYears(1),
                LocalDate.now().plusYears(5), "Police");
    }

    @Test
    void getPassport_notFound_throws() {
        when(passportRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> passportService.getPassport(USER_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void add_alreadyExists_throws() {
        when(passportRepository.existsByUserId(USER_ID)).thenReturn(true);

        assertThatThrownBy(() -> passportService.add(USER_ID, request()))
                .isInstanceOf(AlreadyExistsException.class);

        verify(passportRepository, never()).save(any());
    }

    @Test
    void add_userNotFound_throws() {
        when(passportRepository.existsByUserId(USER_ID)).thenReturn(false);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> passportService.add(USER_ID, request()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void add_success_setsUserAndSaves() {
        Passport passport = new Passport();
        User user = new User();
        user.setId(USER_ID);
        when(passportRepository.existsByUserId(USER_ID)).thenReturn(false);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(passportMapper.toEntity(any())).thenReturn(passport);
        when(passportRepository.save(passport)).thenReturn(passport);
        when(passportMapper.toDto(passport)).thenReturn(mock());

        passportService.add(USER_ID, request());

        assertThat(passport.getUser()).isSameAs(user);
        verify(passportRepository).save(passport);
    }

    @Test
    void update_notFound_throws() {
        when(passportRepository.findByUserId(USER_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> passportService.update(USER_ID, request()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_appliesMapperAndSaves() {
        Passport passport = new Passport();
        when(passportRepository.findByUserId(USER_ID)).thenReturn(Optional.of(passport));
        when(passportRepository.save(passport)).thenReturn(passport);
        when(passportMapper.toDto(passport)).thenReturn(mock());

        passportService.update(USER_ID, request());

        verify(passportMapper).updatePassportFromRequest(any(), any());
        verify(passportRepository).save(passport);
    }

    @Test
    void delete_notFound_throws() {
        when(passportRepository.existsByUserId(USER_ID)).thenReturn(false);

        assertThatThrownBy(() -> passportService.delete(USER_ID))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(passportRepository, never()).deleteByUserId(USER_ID);
    }

    @Test
    void delete_existing_deletesByUserId() {
        when(passportRepository.existsByUserId(USER_ID)).thenReturn(true);

        passportService.delete(USER_ID);

        verify(passportRepository).deleteByUserId(USER_ID);
    }

    private static PassportInfoDto mock() {
        return org.mockito.Mockito.mock(PassportInfoDto.class);
    }
}
