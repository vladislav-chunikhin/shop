package bym.shop.dto;

import bym.shop.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto implements ResponseDto {
    private String fullName;
    private UUID id;

    public static UserResponseDto from(final UserEntity entity) {
        return new UserResponseDto(entity.getFullName(), entity.getId());
    }
}
