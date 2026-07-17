package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.core.exception.InvalidPasswordException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.IntegrationEventMapper;
import am.agrotrade.core.mapper.UserMapper;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.MediaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private MediaService mediaService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IntegrationEventMapper integrationEventMapper;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void get_returnsMappedUser() {
        User user = new User();
        BaseUserInfoDto dto = new BaseUserInfoDto();
        when(userRepository.findByUsernameIgnoreCase("anna")).thenReturn(Optional.of(user));
        when(userMapper.toBaseUserInfoDto(user)).thenReturn(dto);

        assertThat(userService.get("anna")).isSameAs(dto);
    }

    @Test
    void get_notFound_throws() {
        when(userRepository.findByUsernameIgnoreCase("anna")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.get("anna"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_appliesMapperAndPublishesSettingsEvent() {
        User user = new User();
        UpdateUserRequest request = new UpdateUserRequest("Anna", "Petrosyan", null, "Yerevan", "+37411111111", null);
        BaseUserInfoDto dto = new BaseUserInfoDto();
        when(userRepository.findByUsernameIgnoreCase("anna")).thenReturn(Optional.of(user));
        when(userMapper.toBaseUserInfoDto(user)).thenReturn(dto);

        BaseUserInfoDto result = userService.update("anna", request);

        assertThat(result).isSameAs(dto);
        verify(userMapper).updateUserFromRequest(request, user);
        verify(eventPublisher).publishEvent(nullable(Object.class));
    }

    @Test
    void changePassword_wrongOldPassword_throws() {
        User user = new User();
        user.setPassword("ENC-OLD");
        when(userRepository.findByUsernameIgnoreCase("anna")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "ENC-OLD")).thenReturn(false);

        assertThatThrownBy(() -> userService.changePassword("anna",
                new ChangePasswordRequest("wrong", "newPassword1")))
                .isInstanceOf(InvalidPasswordException.class);

        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void changePassword_success_encodesNewPassword() {
        User user = new User();
        user.setPassword("ENC-OLD");
        when(userRepository.findByUsernameIgnoreCase("anna")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPassword1", "ENC-OLD")).thenReturn(true);
        when(passwordEncoder.encode("newPassword1")).thenReturn("ENC-NEW");
        when(userMapper.toBaseUserInfoDto(user)).thenReturn(new BaseUserInfoDto());

        userService.changePassword("anna", new ChangePasswordRequest("oldPassword1", "newPassword1"));

        assertThat(user.getPassword()).isEqualTo("ENC-NEW");
    }
}
