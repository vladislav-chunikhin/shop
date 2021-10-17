package bym.shop.basket;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.BasketController;
import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.basket.BasketRequestDto;
import bym.shop.dto.basket.BasketResponseDto;
import bym.shop.dto.basket.OrderItemRequestDto;
import bym.shop.elasticsearch.OrderInfo;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collection;

import static bym.shop.constants.BaseURL.BASKET_BASE_URL;

/**
 * Integration tests for {@link BasketController#createShoppingCart(BasketRequestDto)}.
 */
public class BasketCreationIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/basket/insert-basket.sql")
    @SqlAfter("/sql/basket/delete-basket.sql")
    @Test
    public void basketCreationInTheUsualCase() throws Exception {
        final BasketRequestDto expectedRequest = new BasketRequestDto(
            "53204f67-4563-4e48-8af0-d253d15e31c9",
                Arrays.asList(
                       new OrderItemRequestDto(2, "4d4b6161-dd5f-410a-83cc-41b247452f3e"),
                       new OrderItemRequestDto(1, "a0c4e0c4-34b4-4617-b8de-4597b1bad553"),
                       new OrderItemRequestDto(4, "b1745ff0-34d5-408e-bb86-cf9a47a10598"),
                       new OrderItemRequestDto(2, "ecc219bc-9408-4cab-9eda-7717edd39d4b"),
                       new OrderItemRequestDto(6, "461d2aa0-ceaf-4f48-80d5-aa1ae6fe10c1")
                )
        );

        final ResultActions res = executePost(BASKET_BASE_URL, expectedRequest);
        checkForSuccess(res);

        final CommonArrayResponseDto<BasketResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        Assertions.assertEquals(5, response.getData().size());

        final Collection<OrderInfo> orders = orderInfoElasticSearchRepository.findAllByProductsContaining("be");
        Assertions.assertFalse(orders.isEmpty());

        orderInfoElasticSearchRepository.deleteAll();
    }

}
