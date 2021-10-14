package bym.shop.controller;

import bym.shop.service.OrderItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static bym.shop.constants.BaseURL.ORDER_ITEM_BASE_URL;

@RestController
@RequestMapping(ORDER_ITEM_BASE_URL)
@Tag(name = "Order item API")
@RequiredArgsConstructor
public class OrderItemController {
    private final OrderItemService orderItemService;
}
