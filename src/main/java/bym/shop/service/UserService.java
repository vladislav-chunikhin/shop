package bym.shop.service;

import bym.shop.dto.UserRequestDto;
import bym.shop.dto.UserResponseDto;
import bym.shop.entity.UserEntity;
import bym.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto request) {
        UserEntity user = new UserEntity();
        user.setFullName(request.getFullName());
        return UserResponseDto.from(userRepository.save(user));
    }
}
