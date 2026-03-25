package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.LoginRequest;
import am.agrotrade.common.dto.user.request.RegisterRequest;
import am.agrotrade.common.dto.user.response.AuthResponse;
import am.agrotrade.common.enums.Role;
import am.agrotrade.core.mapper.UserMapper;
import am.agrotrade.core.model.RefreshToken;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.AuthService;
import am.agrotrade.core.service.RefreshTokenService;
import am.agrotrade.core.service.security.JwtService;
import am.agrotrade.core.service.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService  jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private  final UserMapper userMapper;


    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameNotFoundException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email is already taken");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(Role.BUYER));
        user.setActive(true);

        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(new UserPrincipal(savedUser));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String accessToken = jwtService.generateAccessToken(new UserPrincipal(user));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @Override
    public AuthResponse refresh(String refreshTokenStr) {
        RefreshToken newRefreshToken = refreshTokenService.verifyAndRotate(refreshTokenStr);

        User user = newRefreshToken.getUser();

        String newAccessToken = jwtService.generateAccessToken(new UserPrincipal(user));

        return new AuthResponse(newAccessToken, newRefreshToken.getToken());
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
}
