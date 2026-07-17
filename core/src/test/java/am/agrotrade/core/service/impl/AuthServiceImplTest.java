package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.user.LoginDto;
import am.agrotrade.common.dto.user.RefreshTokenDto;
import am.agrotrade.common.dto.user.RegisterDto;
import am.agrotrade.common.dto.user.VerifyDto;
import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.enums.Gender;
import am.agrotrade.common.enums.Role;
import am.agrotrade.core.exception.InvalidCredentialsException;
import am.agrotrade.core.exception.InvalidVerificationCodeException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.exception.UserAlreadyExistsException;
import am.agrotrade.core.exception.UserAlreadyVerifiedException;
import am.agrotrade.core.exception.UserNotVerifiedException;
import am.agrotrade.core.exception.VerificationCodeExpiredException;
import am.agrotrade.core.mapper.IntegrationEventMapper;
import am.agrotrade.core.mapper.UserMapper;
import am.agrotrade.core.model.RefreshToken;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.security.JwtService;
import am.agrotrade.core.service.RefreshTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserMapper userMapper;
    @Mock
    private IntegrationEventMapper integrationEventMapper;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest("Anna", "Petrosyan", Gender.MALE, LocalDate.of(1990, 1, 1),
                "Yerevan", "anna@mail.com", "+37411111111", "anna", "secret123",
                true, false, true);
    }

    @Test
    void register_usernameTaken_throws() {
        when(userRepository.existsByUsernameIgnoreCase("anna")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(registerRequest))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("Username is already taken");

        verify(userRepository, never()).save(any());
    }

    @Test
    void register_emailTaken_throws() {
        when(userRepository.existsByUsernameIgnoreCase("anna")).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase("anna@mail.com")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(registerRequest))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("Email is already taken");
    }

    @Test
    void register_success_encodesPassword_savesInactiveUser_andPublishesEvent() {
        User user = new User();
        user.setPassword("secret123");
        when(userRepository.existsByUsernameIgnoreCase("anna")).thenReturn(false);
        when(userRepository.existsByEmailIgnoreCase("anna@mail.com")).thenReturn(false);
        when(userMapper.toEntity(registerRequest)).thenReturn(user);
        when(passwordEncoder.encode("secret123")).thenReturn("ENC");
        when(userRepository.save(user)).thenReturn(user);

        RegisterDto result = authService.register(registerRequest);

        assertThat(result.success()).isTrue();
        assertThat(user.getPassword()).isEqualTo("ENC");
        assertThat(user.isActive()).isFalse();
        assertThat(user.getRoles()).containsExactly(Role.BUYER);
        assertThat(user.getVerificationCode()).isNotNull();
        assertThat(user.getVerificationExpiration()).isAfter(LocalDateTime.now());
        verify(eventPublisher).publishEvent(nullable(Object.class));
    }

    @Test
    void verify_success_activatesAndClearsCode() {
        User user = new User();
        user.setActive(false);
        user.setVerificationCode("123456");
        user.setVerificationExpiration(LocalDateTime.now().plusMinutes(5));
        when(userRepository.findByEmailIgnoreCase("anna@mail.com")).thenReturn(Optional.of(user));

        VerifyDto result = authService.verify("anna@mail.com", "123456");

        assertThat(result.success()).isTrue();
        assertThat(user.isActive()).isTrue();
        assertThat(user.getVerificationCode()).isNull();
        assertThat(user.getVerificationExpiration()).isNull();
        verify(userRepository).save(user);
    }

    @Test
    void verify_userNotFound_throws() {
        when(userRepository.findByEmailIgnoreCase("x@mail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.verify("x@mail.com", "123456"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void verify_alreadyActive_throws() {
        User user = new User();
        user.setActive(true);
        when(userRepository.findByEmailIgnoreCase("anna@mail.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.verify("anna@mail.com", "123456"))
                .isInstanceOf(UserAlreadyVerifiedException.class);
    }

    @Test
    void verify_wrongCode_throws() {
        User user = new User();
        user.setActive(false);
        user.setVerificationCode("111111");
        when(userRepository.findByEmailIgnoreCase("anna@mail.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.verify("anna@mail.com", "222222"))
                .isInstanceOf(InvalidVerificationCodeException.class);
    }

    @Test
    void verify_expiredCode_throws() {
        User user = new User();
        user.setActive(false);
        user.setVerificationCode("123456");
        user.setVerificationExpiration(LocalDateTime.now().minusMinutes(1));
        when(userRepository.findByEmailIgnoreCase("anna@mail.com")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.verify("anna@mail.com", "123456"))
                .isInstanceOf(VerificationCodeExpiredException.class);
    }

    @Test
    void login_userNotFound_throwsInvalidCredentials() {
        when(userRepository.findByUsernameIgnoreCase("anna")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(new LoginRequest("anna", "secret123")))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void login_userNotActive_throws() {
        User user = new User();
        user.setActive(false);
        when(userRepository.findByUsernameIgnoreCase("anna")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.login(new LoginRequest("anna", "secret123")))
                .isInstanceOf(UserNotVerifiedException.class);
    }

    @Test
    void login_badCredentials_translatedToInvalidCredentials() {
        User user = new User();
        user.setActive(true);
        when(userRepository.findByUsernameIgnoreCase("anna")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("bad"));

        assertThatThrownBy(() -> authService.login(new LoginRequest("anna", "wrong123")))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    @Test
    void login_success_returnsTokens() {
        User user = new User();
        user.setActive(true);
        RefreshToken rt = RefreshToken.builder().token("refresh-tok").user(user).build();
        when(userRepository.findByUsernameIgnoreCase("anna")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateAccessToken(any())).thenReturn("access-tok");
        when(refreshTokenService.createRefreshToken(user)).thenReturn(rt);

        LoginDto result = authService.login(new LoginRequest("anna", "secret123"));

        assertThat(result.accessToken()).isEqualTo("access-tok");
        assertThat(result.refreshToken()).isEqualTo("refresh-tok");
    }

    @Test
    void refresh_rotatesTokenAndIssuesNewAccessToken() {
        User user = new User();
        RefreshToken rotated = RefreshToken.builder().token("new-refresh").user(user).build();
        when(refreshTokenService.verifyAndRotate("old")).thenReturn(rotated);
        when(jwtService.generateAccessToken(any())).thenReturn("new-access");

        RefreshTokenDto result = authService.refresh("old");

        assertThat(result.accessToken()).isEqualTo("new-access");
        assertThat(result.refreshToken()).isEqualTo("new-refresh");
    }

    @Test
    void logout_revokesAllRefreshTokens() {
        authService.logout(42L);

        verify(refreshTokenService).revokeAllForUser(42L);
    }

    @Test
    void delete_deactivatesUser() {
        User user = new User();
        user.setActive(true);
        when(userRepository.findById(42L)).thenReturn(Optional.of(user));

        authService.delete(42L);

        assertThat(user.isActive()).isFalse();
    }

    @Test
    void delete_userNotFound_throws() {
        when(userRepository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.delete(42L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
