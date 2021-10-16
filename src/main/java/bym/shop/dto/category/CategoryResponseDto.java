package bym.shop.dto.category;

import bym.shop.dto.ResponseDto;
import bym.shop.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto implements ResponseDto {
    private String name;
    private UUID id;

    public static CategoryResponseDto from(@NonNull final CategoryEntity entity) {
        return new CategoryResponseDto(entity.getName(), entity.getId());
    }
}
