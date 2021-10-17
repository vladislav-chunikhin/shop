package bym.shop.dto.basket.request;

import bym.shop.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketRequestDto implements RequestDto {

    @NotBlank
    private String orderId;

    @NotEmpty
    private Collection<OrderItemRequestDto> items;

}
