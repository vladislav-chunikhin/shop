package bym.shop.controller;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.product.ProductRequestDto;
import bym.shop.dto.product.ProductResponseDto;
import bym.shop.exception.ErrorResponseDto;
import bym.shop.service.ProductService;
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

import static bym.shop.constants.BaseURL.PRODUCT_BASE_URL;

@RestController
@RequestMapping(PRODUCT_BASE_URL)
@Tag(name = "Product API")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "API to create a product")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Creation successful",
                    content = {@Content(schema = @Schema(implementation = ProductResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Name not specified || price is less than zero || sku not specified || category id not specified",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody final ProductRequestDto request) {
        return productService.create(request);
    }

    @Operation(summary = "API to update a product")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Updating successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Name not specified || price is less than zero || sku not specified || category id not specified",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid product ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product is not found",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody final ProductRequestDto request, @PathVariable final String id) {
        productService.update(UUID.fromString(id), request);
    }

    @Operation(summary = "API to get products by identifiers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting successful",
                    content = {@Content(schema = @Schema(implementation = CommonArrayResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid product ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @GetMapping
    public CommonArrayResponseDto<ProductResponseDto> get(@RequestParam(required = false) final Collection<String> ids) {
        return productService.get(ids);
    }

    @Operation(summary = "API to delete a product (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleting successful"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid product ID format",
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
        productService.delete(UUID.fromString(id));
    }
}
