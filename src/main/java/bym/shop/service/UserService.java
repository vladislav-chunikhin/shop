package bym.shop.service;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.user.UserRequestDto;
import bym.shop.dto.user.UserResponseDto;
import bym.shop.entity.UserEntity;
import bym.shop.exception.ResourceDeletedException;
import bym.shop.repository.UserRepository;
import bym.shop.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto create(@NonNull final UserRequestDto request) {
        final UserEntity user = new UserEntity();
        user.setFullName(request.getFullName());
        return UserResponseDto.from(userRepository.save(user));
    }

    public void update(@NonNull final UUID id, @NonNull final UserRequestDto request) {
        final UserEntity user = userRepository.findByIdAndDeletedIsNull(id).orElseThrow(() -> new EntityNotFoundException("User is not found"));
        user.setFullName(request.getFullName());
        userRepository.save(user);
    }

    public CommonArrayResponseDto<UserResponseDto> get(@Nullable final Collection<String> ids) {
        return CommonUtil.getByIds(ids, UserResponseDto::from, userRepository::findAllByDeletedIsNull, userRepository::findAllByDeletedIsNullAndIdIn);
    }

    public void delete(@NonNull final UUID id) {
        final UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User is not found"));
        if (user.getDeleted() != null) throw new ResourceDeletedException("User has already been deleted");
        user.setDeleted(LocalDateTime.now());
        userRepository.save(user);
    }

}
