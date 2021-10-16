package bym.shop.product;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.ProductController;
import bym.shop.entity.ProductEntity;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

import static bym.shop.constants.BaseURL.PRODUCT_BASE_URL;

/**
 * Integration tests for {@link ProductController#delete(String)}.
 */
public class ProductDeletingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/product/insert-product.sql")
    @SqlAfter("/sql/product/delete-product.sql")
    @Test
    public void productDeletingInTheUsualCase() throws Exception {
        final ResultActions res = executeDelete(PRODUCT_BASE_URL + "/4d4b6161-dd5f-410a-83cc-41b247452f3e");
        checkForNoContent(res);

        final Optional<ProductEntity> product = productRepository.findById(UUID.fromString("4d4b6161-dd5f-410a-83cc-41b247452f3e"));
        Assertions.assertTrue(product.isPresent());
        Assertions.assertNotNull(product.get().getDeleted());
    }
}
