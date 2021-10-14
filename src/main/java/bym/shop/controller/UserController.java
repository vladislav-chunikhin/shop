package bym.shop.controller;

import bym.shop.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static bym.shop.constants.BaseURL.USER_BASE_URL;

@RestController
@RequestMapping(USER_BASE_URL)
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void createUser() {
        //todo
    }

}
