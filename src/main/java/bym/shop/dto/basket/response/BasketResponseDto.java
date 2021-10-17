package bym.shop.dto.basket.response;

import bym.shop.dto.ResponseDto;
import bym.shop.entity.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponseDto implements ResponseDto {

    private UUID id;

    private BigDecimal price;

    private Integer quantity;

    private UUID productId;

    private UUID orderId;

    public static BasketResponseDto from(@NonNull final OrderItemEntity entity) {
        return new BasketResponseDto(
                entity.getId(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getProductId(),
                entity.getOrderId()
        );
    }
}
