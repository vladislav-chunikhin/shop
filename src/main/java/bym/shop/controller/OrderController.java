package bym.shop.controller;

import bym.shop.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static bym.shop.constants.BaseURL.ORDER_BASE_URL;

@RestController
@RequestMapping(ORDER_BASE_URL)
@Tag(name = "Order API")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

}
