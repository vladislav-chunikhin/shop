package bym.shop.order;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.OrderController;
import bym.shop.dto.order.OrderRequestDto;
import bym.shop.entity.OrderEntity;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

import static bym.shop.constants.BaseURL.ORDER_BASE_URL;

/**
 * Integration tests for {@link OrderController#update(OrderRequestDto, String)}.
 */
public class OrderUpdatingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/order/insert-order.sql")
    @SqlAfter("/sql/order/delete-order.sql")
    @Test
    public void orderUpdatingInTheUsualCase() throws Exception {
        final OrderRequestDto expectedRequest = new OrderRequestDto("c41ac77a-2449-43d5-818c-d00fd03668ba");

        final ResultActions res = executePut(ORDER_BASE_URL + "/53204f67-4563-4e48-8af0-d253d15e31c9", expectedRequest);
        checkForNoContent(res);

        final Optional<OrderEntity> order = orderRepository.findById(UUID.fromString("53204f67-4563-4e48-8af0-d253d15e31c9"));
        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(UUID.fromString("c41ac77a-2449-43d5-818c-d00fd03668ba"), order.get().getUserId());
    }

}
