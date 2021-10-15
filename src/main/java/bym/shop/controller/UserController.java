package bym.shop.controller;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.UserRequestDto;
import bym.shop.dto.UserResponseDto;
import bym.shop.exception.ErrorResponseDto;
import bym.shop.service.UserService;
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

import static bym.shop.constants.BaseURL.USER_BASE_URL;

@RestController
@RequestMapping(USER_BASE_URL)
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "API to create a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Creation successful",
                    content = {@Content(schema = @Schema(implementation = UserResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Full username not specified",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid user ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PostMapping
    public UserResponseDto create(@Valid @RequestBody final UserRequestDto request) {
        return userService.create(request);
    }

    @Operation(summary = "API to update a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Updating successful",
                    content = {@Content(schema = @Schema(implementation = UserResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Full username not specified",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid user ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User is not found",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody final UserRequestDto request, @PathVariable final String id) {
        userService.update(UUID.fromString(id), request);
    }

    @Operation(summary = "API to get users by identifiers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Getting successful",
                    content = {@Content(schema = @Schema(implementation = CommonArrayResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid user ID format",
                    content = {@Content(schema = @Schema(implementation = ErrorResponseDto.class))}
            )
    })
    @GetMapping
    public CommonArrayResponseDto<UserResponseDto> get(@RequestParam(required = false) final Collection<String> ids) {
        return userService.get(ids);
    }

    @Operation(summary = "API to delete a user (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleting successful",
                    content = {@Content(schema = @Schema(implementation = UserResponseDto.class))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Invalid user ID format",
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
        userService.delete(UUID.fromString(id));
    }

}

