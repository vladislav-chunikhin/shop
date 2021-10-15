package bym.shop.service;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.UserRequestDto;
import bym.shop.dto.UserResponseDto;
import bym.shop.entity.UserEntity;
import bym.shop.exception.ResourceDeletedException;
import bym.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        final UserEntity user = userRepository.findByIdAndDeletedIsNull(id).orElseThrow(() -> new EntityNotFoundException("User is not found"));
        user.setFullName(request.getFullName());
        userRepository.save(user);
    }

    public CommonArrayResponseDto<UserResponseDto> get(@Nullable final Collection<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return new CommonArrayResponseDto<>(userRepository.findAll().stream().map(UserResponseDto::from).collect(Collectors.toList()));
        final List<UUID> idsAsUUID = ids.stream().map(UUID::fromString).collect(Collectors.toList());
        return new CommonArrayResponseDto<>(userRepository.findAllByDeletedIsNullAndIdIn(idsAsUUID).stream().map(UserResponseDto::from).collect(Collectors.toList()));
    }

    public void delete(final UUID id) {
        final UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User is not found"));
        if (user.getDeleted() != null) throw new ResourceDeletedException("User has already been deleted");
        user.setDeleted(LocalDateTime.now());
        userRepository.save(user);
    }

}
