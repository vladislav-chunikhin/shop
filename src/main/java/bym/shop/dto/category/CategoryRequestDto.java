package bym.shop.dto.category;

import bym.shop.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto implements RequestDto {
    @NotBlank
    private String name;
}
