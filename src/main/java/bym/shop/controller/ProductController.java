package bym.shop.controller;

import bym.shop.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static bym.shop.constants.BaseURL.PRODUCT_BASE_URL;

@RestController
@RequestMapping(PRODUCT_BASE_URL)
@Tag(name = "Product API")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

}
