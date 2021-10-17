package bym.shop.basket;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.BasketController;
import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.basket.response.BasketResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static bym.shop.constants.BaseURL.BASKET_BASE_URL;

/**
 * Integration tests for {@link BasketController#get(String)}.
 */
public class BasketGettingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/order/insert-order-getting-by-product-name.sql")
    @SqlAfter("/sql/order/delete-order.sql")
    @Test
    public void basketGettingInTheUsualCase() throws Exception {
        final ResultActions res = executeGet(BASKET_BASE_URL + "/53204f67-4563-4e48-8af0-d253d15e31c9");
        checkForSuccess(res);

        final CommonArrayResponseDto<BasketResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(3, response.getData().size());
    }

}
