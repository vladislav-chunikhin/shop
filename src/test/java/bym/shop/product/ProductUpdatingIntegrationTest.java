package bym.shop.product;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.ProductController;
import bym.shop.dto.product.ProductRequestDto;
import bym.shop.elasticsearch.Order;
import bym.shop.entity.ProductEntity;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.apache.commons.compress.utils.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.*;

import static bym.shop.constants.BaseURL.PRODUCT_BASE_URL;

/**
 * Integration tests for {@link ProductController#update(ProductRequestDto, String)}.
 */
public class ProductUpdatingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/product/insert-product.sql")
    @SqlAfter("/sql/product/delete-product.sql")
    @Test
    public void productUpdatingInTheUsualCase() throws Exception {
        final ProductRequestDto expectedRequest = getExpectedRequest();

        final ResultActions res = executePut(PRODUCT_BASE_URL + "/4d4b6161-dd5f-410a-83cc-41b247452f3e", expectedRequest);
        checkForNoContent(res);

        final Optional<ProductEntity> product = productRepository.findById(UUID.fromString("4d4b6161-dd5f-410a-83cc-41b247452f3e"));
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals("yoyo", product.get().getName());
        Assertions.assertEquals(BigDecimal.valueOf(24.55), product.get().getPrice());
    }

    @SqlBefore("/sql/order/insert-order-getting-by-product-name.sql")
    @SqlAfter("/sql/order/delete-order.sql")
    @Test
    public void productUpdatingWhenNeedToUpdateSearchIndex() throws Exception {
        final ProductRequestDto expectedRequest = getExpectedRequest();
        final List<Order> expectedOrders = Arrays.asList(
                new Order(
                        UUID.randomUUID(),
                        UUID.fromString("777acc1f-a93c-4d4f-9733-a9650b8a62f0"),
                        UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"),
                        BigDecimal.valueOf(2355),
                        Sets.newHashSet("beer", "smth")
                ),
                new Order(
                        UUID.randomUUID(),
                        UUID.fromString("53204f67-4563-4e48-8af0-d253d15e31c9"),
                        UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"),
                        BigDecimal.valueOf(7890),
                        Sets.newHashSet("bear", "doll", "cupboard")
                )
        );
        orderElasticSearchRepository.saveAll(expectedOrders);

        final ResultActions res = executePut(PRODUCT_BASE_URL + "/4d4b6161-dd5f-410a-83cc-41b247452f3e", expectedRequest);
        checkForNoContent(res);

        final Optional<ProductEntity> product = productRepository.findById(UUID.fromString("4d4b6161-dd5f-410a-83cc-41b247452f3e"));
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals("yoyo", product.get().getName());
        Assertions.assertEquals(BigDecimal.valueOf(24.55), product.get().getPrice());

        final Collection<Order> ordersWithBear = orderElasticSearchRepository.findAllByProductsContaining("bear");
        Assertions.assertEquals(0, ordersWithBear.size());

        final Collection<Order> ordersWithYoyo = orderElasticSearchRepository.findAllByProductsContaining("yoyo");
        Assertions.assertEquals(2, ordersWithYoyo.size());

        orderElasticSearchRepository.deleteAll();
    }

    private ProductRequestDto getExpectedRequest() {
        return new ProductRequestDto(
                "yoyo",
                BigDecimal.valueOf(24.55),
                "UGG-BB-PUR-06",
                "450baeb2-2908-4998-82ac-173c5b699df4"
        );
    }

}
