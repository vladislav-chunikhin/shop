package bym.shop.dto.order;

import bym.shop.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto implements RequestDto {

    @NotBlank
    private String userId;

}
