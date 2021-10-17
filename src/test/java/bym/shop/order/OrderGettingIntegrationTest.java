package bym.shop.order;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.OrderController;
import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.order.OrderResponseDto;
import bym.shop.elasticsearch.Order;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.*;

import static bym.shop.constants.BaseURL.ORDER_BASE_URL;

/**
 * Integration tests for {@link OrderController#get(Collection, String)}.
 */
public class OrderGettingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/order/insert-order.sql")
    @SqlAfter("/sql/order/delete-order.sql")
    @Test
    public void orderGettingInTheUsualCase() throws Exception {
        final ResultActions res = executeGet(ORDER_BASE_URL + "?ids=53204f67-4563-4e48-8af0-d253d15e31c9");
        checkForSuccess(res);
        final CommonArrayResponseDto<OrderResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(1, response.getData().size());
        final List<UUID> expectedUserIds = Collections.singletonList(UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"));
        response.getData().forEach(it -> Assertions.assertTrue(expectedUserIds.contains(it.getUserId())));
    }

    @SqlBefore("/sql/order/insert-order.sql")
    @SqlAfter("/sql/order/delete-order.sql")
    @Test
    public void orderGettingWhenIdsAreMissing() throws Exception {
        final ResultActions res = executeGet(ORDER_BASE_URL);
        checkForSuccess(res);
        final CommonArrayResponseDto<OrderResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(1, response.getData().size());
        final List<UUID> expectedUserIds = Collections.singletonList(UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"));
        response.getData().forEach(it -> Assertions.assertTrue(expectedUserIds.contains(it.getUserId())));
    }

    @SqlBefore("/sql/order/insert-order-getting-by-product-name.sql")
    @SqlAfter("/sql/order/delete-order.sql")
    @Test
    public void orderGettingByProductNameEqualsBe() throws Exception {
        final List<Order> expectedOrders = getExpectedOrders();
        orderElasticSearchRepository.saveAll(expectedOrders);

        final ResultActions res = executeGet(ORDER_BASE_URL + "?product=be");
        checkForSuccess(res);

        final CommonArrayResponseDto<OrderResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(2, response.getData().size());

        orderElasticSearchRepository.deleteAll();
    }

    @SqlBefore("/sql/order/insert-order-getting-by-product-name.sql")
    @SqlAfter("/sql/order/delete-order.sql")
    @Test
    public void orderGettingByProductNameEqualsSm() throws Exception {
        final List<Order> expectedOrders = getExpectedOrders();
        orderElasticSearchRepository.saveAll(expectedOrders);

        final ResultActions res = executeGet(ORDER_BASE_URL + "?product=sm");
        checkForSuccess(res);

        final CommonArrayResponseDto<OrderResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(1, response.getData().size());
        response.getData().forEach(it -> Assertions.assertEquals(BigDecimal.valueOf(2355.0), it.getTotalAmount()));

        orderElasticSearchRepository.deleteAll();
    }

    private List<Order> getExpectedOrders() {
        return Arrays.asList(
                new Order(
                        UUID.randomUUID(),
                        UUID.fromString("777acc1f-a93c-4d4f-9733-a9650b8a62f0"),
                        UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"),
                        BigDecimal.valueOf(2355),
                        Arrays.asList("beer", "smth")
                ),
                new Order(
                        UUID.randomUUID(),
                        UUID.fromString("53204f67-4563-4e48-8af0-d253d15e31c9"),
                        UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"),
                        BigDecimal.valueOf(7890),
                        Arrays.asList("bear", "doll", "cupboard")
                )
        );
    }

}
