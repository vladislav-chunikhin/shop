package bym.shop.controller;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.category.CategoryRequestDto;
import bym.shop.dto.category.CategoryResponseDto;
import bym.shop.exception.ErrorResponseDto;
import bym.shop.service.CategoryService;
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

import static bym.shop.constants.BaseURL.CATEGORY_BASE_URL;

@RestController
@RequestMapping(CATEGORY_BASE_URL)
@Tag(name = "Category API")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "API to create a category")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Creation successful",
                    content = {@Content(schema = @Schema(implementation = CategoryResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Name not specified",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PostMapping
    public CategoryResponseDto create(@Valid @RequestBody final CategoryRequestDto request) {
        return categoryService.create(request);
    }

    @Operation(summary = "API to update a category")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Updating successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Name not specified",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid category ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category is not found",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody final CategoryRequestDto request, @PathVariable final String id) {
        categoryService.update(UUID.fromString(id), request);
    }

    @Operation(summary = "API to get categories by identifiers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting successful",
                    content = {@Content(schema = @Schema(implementation = CommonArrayResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid category ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @GetMapping
    public CommonArrayResponseDto<CategoryResponseDto> get(@RequestParam(required = false) final Collection<String> ids) {
        return categoryService.get(ids);
    }

    @Operation(summary = "API to delete a category (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleting successful"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid category ID format",
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
        categoryService.delete(UUID.fromString(id));
    }
}
