package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.ResendCodeDto;
import am.agrotrade.common.dto.user.LoginDto;
import am.agrotrade.common.dto.user.RefreshTokenDto;
import am.agrotrade.common.dto.user.RegisterDto;
import am.agrotrade.common.dto.user.VerifyDto;
import am.agrotrade.common.enums.Role;
import am.agrotrade.core.exception.InvalidCredentialsException;
import am.agrotrade.core.exception.InvalidVerificationCodeException;
import am.agrotrade.core.exception.UserAlreadyExistsException;
import am.agrotrade.core.exception.UserAlreadyVerifiedException;
import am.agrotrade.core.exception.UserNotFoundException;
import am.agrotrade.core.exception.UserNotVerifiedException;
import am.agrotrade.core.exception.VerificationCodeExpiredException;
import am.agrotrade.core.mapper.UserMapper;
import am.agrotrade.core.model.RefreshToken;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.AuthService;
import am.agrotrade.core.service.RefreshTokenService;
import am.agrotrade.core.security.JwtService;
import am.agrotrade.core.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;


    @Override
    public RegisterDto register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email is already taken");
        }

        String verificationCode = generateVerificationCode();

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Role.BUYER));
        user.setActive(false);
        user.setVerificationCode(verificationCode);
        user.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));

        userRepository.save(user);

        return new RegisterDto(
                true,
                "User registered successfully. Please verify your email.");
    }

    @Override
    public VerifyDto verify(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.isActive()) {
            throw new UserAlreadyVerifiedException("User is already verified");
        }

        if (!code.equals(user.getVerificationCode())) {
            throw new InvalidVerificationCodeException("Invalid verification code");
        }


        if (user.getVerificationExpiration().isBefore(LocalDateTime.now())) {
            throw new VerificationCodeExpiredException("Verification code has expired. Please request a new one.");
        }

        user.setActive(true);
        user.setVerificationCode(null);
        user.setVerificationExpiration(null);

        userRepository.save(user);

        return new VerifyDto(
                true,
                "Account successfully verified");
    }

    @Override
    public ResendCodeDto resendVerificationCode(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.isActive()) {
            throw new UserAlreadyVerifiedException("User is already verified");
        }

        setNewVerificationCode(user);

        userRepository.save(user);

        return new ResendCodeDto(
                "New verification code has been sent to your email",
                email
        );
    }

    @Override
    public LoginDto login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!user.isActive()) {
            throw new UserNotVerifiedException("Please verify your account before logging in");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        }catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        String accessToken = jwtService.generateAccessToken(new UserPrincipal(user));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new LoginDto(
                accessToken,
                refreshToken.getToken(),
                "Bearer");
    }

    @Override
    public RefreshTokenDto refresh(String refreshTokenStr) {
        RefreshToken newRefreshToken = refreshTokenService.verifyAndRotate(refreshTokenStr);

        User user = newRefreshToken.getUser();

        String newAccessToken = jwtService.generateAccessToken(new UserPrincipal(user));

        return new RefreshTokenDto(
                newAccessToken,
                newRefreshToken.getToken(),
                "Bearer");
    }

    @Override
    public void logout(long userId) {
        refreshTokenService.revokeAllForUser(userId);
    }

    @Override
    public void delete(long userId) {

    }

    @Override
    public List<BaseUserInfoDto> findAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public long findUserIdByRole(String role) {
        return 0;
    }

    private String generateVerificationCode() {
        return String.format("%06d", new java.util.Random().nextInt(999999));
    }

    private void setNewVerificationCode(User user) {
        String code = generateVerificationCode();
        user.setVerificationCode(code);
        user.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));
    }
}
