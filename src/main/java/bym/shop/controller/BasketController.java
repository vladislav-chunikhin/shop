package bym.shop.controller;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.basket.request.BasketRequestDto;
import bym.shop.dto.basket.response.BasketResponseDto;
import bym.shop.dto.basket.request.BasketUpdateRequestDto;
import bym.shop.exception.ErrorResponseDto;
import bym.shop.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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
                    description = "Order id not specified or items are empty",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PostMapping
    public CommonArrayResponseDto<BasketResponseDto> create(@Valid @RequestBody final BasketRequestDto request) {
        return basketService.create(request);
    }

    @Operation(summary = "API to update a basket")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Updating successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order id not specified or items are empty",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid order ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order is not found",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody final BasketUpdateRequestDto request, @PathVariable final String id) {
        basketService.update(UUID.fromString(id), request);
    }

    @Operation(summary = "API to delete a basket (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleting successful"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid order ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "410",
                    description = "Resource has already been deleted",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}

            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String id) {
        basketService.delete(UUID.fromString(id));
    }

    @Operation(summary = "API to get a basket by identifier")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting successful",
                    content = {@Content(schema = @Schema(implementation = CommonArrayResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid order ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @GetMapping("/{id}")
    public CommonArrayResponseDto<BasketResponseDto> get(@PathVariable final String id) {
        return basketService.get(UUID.fromString(id));
    }
}
