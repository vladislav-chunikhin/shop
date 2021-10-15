package bym.shop.controller;

import bym.shop.dto.UserRequestDto;
import bym.shop.dto.UserResponseDto;
import bym.shop.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.UUID;

import static bym.shop.constants.BaseURL.USER_BASE_URL;

@RestController
@RequestMapping(USER_BASE_URL)
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto create(@Valid @RequestBody final UserRequestDto request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody final UserRequestDto request, @PathVariable final String id) {
        userService.update(UUID.fromString(id), request);
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@PathVariable final String id) {
        return userService.get(UUID.fromString(id));
    }

}

