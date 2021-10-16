package bym.shop.controller;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.order.OrderRequestDto;
import bym.shop.dto.order.OrderResponseDto;
import bym.shop.exception.ErrorResponseDto;
import bym.shop.service.OrderService;
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
import java.util.Collection;
import java.util.UUID;

import static bym.shop.constants.BaseURL.ORDER_BASE_URL;

@RestController
@RequestMapping(ORDER_BASE_URL)
@Tag(name = "Order API")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "API to create an order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Creation successful",
                    content = {@Content(schema = @Schema(implementation = OrderResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User id not specified",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PostMapping
    public OrderResponseDto create(@Valid @RequestBody final OrderRequestDto request) {
        return orderService.create(request);
    }

    @Operation(summary = "API to update an order")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Updating successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User id not specified",
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
    public void update(@Valid @RequestBody final OrderRequestDto request, @PathVariable final String id) {
        orderService.update(UUID.fromString(id), request);
    }

    @Operation(summary = "API to get orders by identifiers")
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
    @GetMapping
    public CommonArrayResponseDto<OrderResponseDto> get(@RequestParam(required = false) final Collection<String> ids) {
        return orderService.get(ids);
    }

    @Operation(summary = "API to delete an order (soft delete)")
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
        orderService.delete(UUID.fromString(id));
    }
}
