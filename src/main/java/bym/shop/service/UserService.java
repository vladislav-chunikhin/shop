package bym.shop.service;

import bym.shop.dto.UserRequestDto;
import bym.shop.dto.UserResponseDto;
import bym.shop.entity.UserEntity;
import bym.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto create(final UserRequestDto request) {
        final UserEntity user = new UserEntity();
        user.setFullName(request.getFullName());
        return UserResponseDto.from(userRepository.save(user));
    }

    public void update(final UUID id, final UserRequestDto request) {
        final UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setFullName(request.getFullName());
        userRepository.save(user);
    }

    public UserResponseDto get(final UUID id) {
        final UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserResponseDto.from(user);
    }

}
