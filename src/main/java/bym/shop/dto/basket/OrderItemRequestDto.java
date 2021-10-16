package bym.shop.dto.basket;

import bym.shop.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto implements RequestDto {

    @NotNull
    @Min(value = 1)
    private Integer quantity;

    @NotBlank
    private String productId;

}
