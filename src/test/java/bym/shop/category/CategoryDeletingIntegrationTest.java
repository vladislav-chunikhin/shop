package bym.shop.category;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.CategoryController;
import bym.shop.entity.CategoryEntity;
import bym.shop.entity.ProductEntity;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static bym.shop.constants.BaseURL.CATEGORY_BASE_URL;

/**
 * Integration tests for {@link CategoryController#delete(String)}.
 */
public class CategoryDeletingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/category/insert-category.sql")
    @SqlAfter("/sql/category/delete-category.sql")
    @Test
    public void categoryDeletingInTheUsualCase() throws Exception {
        final ResultActions res = executeDelete(CATEGORY_BASE_URL + "/21f49af8-bca5-4d3c-b1cb-d019598c7a9b");
        checkForNoContent(res);

        final Optional<CategoryEntity> category = categoryRepository.findById(UUID.fromString("21f49af8-bca5-4d3c-b1cb-d019598c7a9b"));
        Assertions.assertTrue(category.isPresent());
        Assertions.assertNotNull(category.get().getDeleted());

        final Collection<ProductEntity> products = productRepository.findAllByCategoryIdAndDeletedIsNull(UUID.fromString("19184ac3-014c-4c83-89b1-879bd5f583e2"));
        Assertions.assertEquals(1, products.size());
        final List<String> expectedProductNames = Collections.singletonList("cupboard");
        products.forEach(it -> Assertions.assertTrue(expectedProductNames.contains(it.getName())));
    }
}
