package bym.shop.order;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.OrderController;
import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.order.OrderResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static bym.shop.constants.BaseURL.ORDER_BASE_URL;

/**
 * Integration tests for {@link OrderController#get(Collection)}.
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

}
