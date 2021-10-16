package bym.shop.dto.product;

import bym.shop.dto.ResponseDto;
import bym.shop.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto implements ResponseDto {

    private UUID id;

    private String name;

    private BigDecimal price;

    private String sku;

    private UUID categoryId;

    public static ProductResponseDto from(@NonNull final ProductEntity entity) {
        return new ProductResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getSku(),
                entity.getCategoryId()
        );
    }

}
