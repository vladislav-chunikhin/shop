package bym.shop.controller;

import bym.shop.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static bym.shop.constants.BaseURL.CATEGORY_BASE_URL;

@RestController
@RequestMapping(CATEGORY_BASE_URL)
@Tag(name = "Category API")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

}
