package am.agrotrade.core.service.impl;

import am.agrotrade.common.dto.media.MediaDto;
import am.agrotrade.common.dto.user.BaseUserInfoDto;
import am.agrotrade.common.dto.user.request.ChangePasswordRequest;
import am.agrotrade.common.dto.user.request.UpdateUserRequest;
import am.agrotrade.core.exception.InvalidPasswordException;
import am.agrotrade.core.exception.ResourceNotFoundException;
import am.agrotrade.core.mapper.UserMapper;
import am.agrotrade.core.model.User;
import am.agrotrade.core.repository.UserRepository;
import am.agrotrade.core.service.MediaService;
import am.agrotrade.core.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MediaService mediaService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public BaseUserInfoDto get(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public BaseUserInfoDto update(String username, UpdateUserRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(request.name());
        user.setSurname(request.surname());
        user.setBirthDate(request.birthDate());
        user.setPhoneNumber(request.phoneNumber());
        user.setAddress(request.address());

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public BaseUserInfoDto changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("The current password you entered is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        return userMapper.toDto(user);
    }

    @Override
    public MediaDto updateAvatar(long userId, MultipartFile file) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mediaService.saveMedia(file, "users", userId);

    }
}
