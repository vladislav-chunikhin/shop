package bym.shop.controller;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.basket.BasketRequestDto;
import bym.shop.dto.basket.BasketResponseDto;
import bym.shop.exception.ErrorResponseDto;
import bym.shop.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static bym.shop.constants.BaseURL.BASKET_BASE_URL;

@RestController
@RequestMapping(BASKET_BASE_URL)
@Tag(name = "Basket API")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @Operation(summary = "API to create a basket")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Creation successful",
                    content = {@Content(schema = @Schema(implementation = BasketResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Name not specified",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PostMapping
    public CommonArrayResponseDto<BasketResponseDto> create(@Valid @RequestBody final BasketRequestDto request) {
        return basketService.create(request);
    }

}
