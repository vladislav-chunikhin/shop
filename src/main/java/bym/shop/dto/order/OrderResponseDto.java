package bym.shop.dto.order;

import bym.shop.dto.ResponseDto;
import bym.shop.elasticsearch.OrderInfo;
import bym.shop.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto implements ResponseDto {

    private UUID id;

    private UUID userId;

    private BigDecimal totalAmount;

    public static OrderResponseDto from(@NonNull final OrderEntity entity) {
        return new OrderResponseDto(entity.getId(), entity.getUserId(), entity.getTotalAmount());
    }

    public static OrderResponseDto from(@NonNull final OrderInfo order) {
        return new OrderResponseDto(order.getOrderId(), order.getUserId(), order.getTotalAmount());
    }

}
