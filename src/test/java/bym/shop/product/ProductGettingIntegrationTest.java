package bym.shop.product;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.ProductController;
import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.product.ProductResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static bym.shop.constants.BaseURL.PRODUCT_BASE_URL;

/**
 * Integration tests for {@link ProductController#get(Collection)}.
 */
public class ProductGettingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/product/insert-product.sql")
    @SqlAfter("/sql/product/delete-product.sql")
    @Test
    public void productGettingInTheUsualCase() throws Exception {
        final ResultActions res = executeGet(PRODUCT_BASE_URL + "?ids=4d4b6161-dd5f-410a-83cc-41b247452f3e,a0c4e0c4-34b4-4617-b8de-4597b1bad553");
        checkForSuccess(res);

        final CommonArrayResponseDto<ProductResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(2, response.getData().size());
        final List<String> expectedNames = Arrays.asList("bear", "red-doll");
        response.getData().forEach(it -> Assertions.assertTrue(expectedNames.contains(it.getName())));
    }

    @SqlBefore("/sql/product/insert-product.sql")
    @SqlAfter("/sql/product/delete-product.sql")
    @Test
    public void productGettingWhenIdsAreMissing() throws Exception {
        final ResultActions res = executeGet(PRODUCT_BASE_URL);
        checkForSuccess(res);
        final CommonArrayResponseDto<ProductResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(5, response.getData().size());
        final List<String> expectedNames = Arrays.asList("bear", "red-doll", "orange-doll", "blue-doll", "cupboard");
        response.getData().forEach(it -> Assertions.assertTrue(expectedNames.contains(it.getName())));
    }

}
