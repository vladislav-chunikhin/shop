package bym.shop.order;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.OrderController;
import bym.shop.dto.order.OrderRequestDto;
import bym.shop.dto.order.OrderResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.UUID;

import static bym.shop.constants.BaseURL.ORDER_BASE_URL;

/**
 * Integration tests for {@link OrderController#create(OrderRequestDto)}.
 */
public class OrderCreationIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/order/insert-user.sql")
    @SqlAfter("/sql/order/delete-order.sql")
    @Test
    public void orderCreationInTheUsualCase() throws Exception {
        final OrderRequestDto expectedRequest = new OrderRequestDto("c41ac77a-2449-43d5-818c-d00fd03668ba");

        final ResultActions res = executePost(ORDER_BASE_URL, expectedRequest);
        checkForSuccess(res);

        final OrderResponseDto response = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), OrderResponseDto.class);
        Assertions.assertEquals(response.getUserId(), UUID.fromString("c41ac77a-2449-43d5-818c-d00fd03668ba"));
        Assertions.assertEquals(response.getTotalAmount(), BigDecimal.ZERO);
        Assertions.assertNotNull(response.getId());
    }
}
