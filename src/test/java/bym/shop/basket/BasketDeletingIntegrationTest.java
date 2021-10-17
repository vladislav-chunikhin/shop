package bym.shop.basket;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.BasketController;
import bym.shop.elasticsearch.Order;
import bym.shop.entity.OrderEntity;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.apache.commons.compress.utils.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static bym.shop.constants.BaseURL.BASKET_BASE_URL;

/**
 * Integration tests for {@link BasketController#delete(String)}.
 */
public class BasketDeletingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/basket/insert-basket.sql")
    @SqlAfter("/sql/basket/delete-basket.sql")
    @Test
    public void basketDeletingInTheUsualCase() throws Exception {
        final UUID orderId = UUID.fromString("53204f67-4563-4e48-8af0-d253d15e31c9");
        final Order expectedOrder = new Order(
                UUID.randomUUID(),
                orderId,
                UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"),
                BigDecimal.valueOf(2355),
                Sets.newHashSet("beer", "smth")
        );
        orderElasticSearchRepository.save(expectedOrder);

        final ResultActions res = executeDelete(BASKET_BASE_URL + "/53204f67-4563-4e48-8af0-d253d15e31c9");
        checkForNoContent(res);

        final Optional<Order> order = orderElasticSearchRepository.findByOrderId(orderId);
        Assertions.assertFalse(order.isPresent());

        final Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        Assertions.assertTrue(orderEntity.isPresent());
        Assertions.assertNotNull(orderEntity.get().getDeleted());

        orderElasticSearchRepository.deleteAll();
    }

}
