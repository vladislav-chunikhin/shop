package bym.shop.basket;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.BasketController;
import bym.shop.dto.basket.request.BasketUpdateRequestDto;
import bym.shop.dto.basket.request.OrderItemUpdateRequestDto;
import bym.shop.elasticsearch.Order;
import bym.shop.entity.OrderEntity;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.apache.commons.compress.utils.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static bym.shop.constants.BaseURL.BASKET_BASE_URL;

/**
 * Integration tests for {@link BasketController#update(BasketUpdateRequestDto, String)}.
 */
public class BasketUpdatingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/order/insert-order-getting-by-product-name.sql")
    @SqlAfter("/sql/order/delete-order.sql")
    @Test
    public void basketUpdatingInTheUsualCase() throws Exception {
        final Order expectedOrder = new Order(
                UUID.randomUUID(),
                UUID.fromString("53204f67-4563-4e48-8af0-d253d15e31c9"),
                UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"),
                BigDecimal.valueOf(2355),
                Sets.newHashSet("beer", "smth")
        );
        orderElasticSearchRepository.save(expectedOrder);

        final BasketUpdateRequestDto expectedRequest = new BasketUpdateRequestDto(
                Arrays.asList(
                        new OrderItemUpdateRequestDto("1d2a2330-3dee-4e20-a755-249fe2b02710",2, "4d4b6161-dd5f-410a-83cc-41b247452f3e"),
                        new OrderItemUpdateRequestDto("dc645c4b-689a-415c-8134-ed061310f0c5",1, "a0c4e0c4-34b4-4617-b8de-4597b1bad553"),
                        new OrderItemUpdateRequestDto("ca883e83-d2fa-4c4d-8093-36f88c94f955",4, "b1745ff0-34d5-408e-bb86-cf9a47a10598")
                )
        );

        final ResultActions res = executePut(BASKET_BASE_URL + "/53204f67-4563-4e48-8af0-d253d15e31c9", expectedRequest);
        checkForNoContent(res);

        final UUID orderId = UUID.fromString("53204f67-4563-4e48-8af0-d253d15e31c9");
        final Optional<OrderEntity> orderEntity = orderRepository.findByIdAndDeletedIsNull(orderId);
        Assertions.assertTrue(orderEntity.isPresent());

        final Optional<Order> order = orderElasticSearchRepository.findByOrderId(orderId);
        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(BigDecimal.valueOf(163.24), order.get().getTotalAmount());

        orderElasticSearchRepository.deleteAll();
    }
}
