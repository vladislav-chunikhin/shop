package bym.shop.product;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.ProductController;
import bym.shop.dto.product.ProductRequestDto;
import bym.shop.entity.ProductEntity;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static bym.shop.constants.BaseURL.PRODUCT_BASE_URL;

/**
 * Integration tests for {@link ProductController#update(ProductRequestDto, String)}.
 */
public class ProductUpdatingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/product/insert-product.sql")
    @SqlAfter("/sql/product/delete-product.sql")
    @Test
    public void productUpdatingInTheUsualCase() throws Exception {
        final ProductRequestDto expectedRequest = new ProductRequestDto(
                "yoyo",
                BigDecimal.valueOf(24.55),
                "UGG-BB-PUR-06",
                "450baeb2-2908-4998-82ac-173c5b699df4"
        );

        final ResultActions res = executePut(PRODUCT_BASE_URL + "/4d4b6161-dd5f-410a-83cc-41b247452f3e", expectedRequest);
        checkForNoContent(res);

        final Optional<ProductEntity> product = productRepository.findById(UUID.fromString("4d4b6161-dd5f-410a-83cc-41b247452f3e"));
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals("yoyo", product.get().getName());
        Assertions.assertEquals(BigDecimal.valueOf(24.55), product.get().getPrice());
    }

}
