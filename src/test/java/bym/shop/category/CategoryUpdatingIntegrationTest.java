package bym.shop.category;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.CategoryController;
import bym.shop.dto.category.CategoryRequestDto;
import bym.shop.entity.CategoryEntity;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

import static bym.shop.constants.BaseURL.CATEGORY_BASE_URL;

/**
 * Integration tests for {@link CategoryController#update(CategoryRequestDto, String)}.
 */
public class CategoryUpdatingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/category/insert-category.sql")
    @SqlAfter("/sql/category/delete-category.sql")
    @Test
    public void categoryUpdatingInTheUsualCase() throws Exception {
        final CategoryRequestDto expectedRequest = new CategoryRequestDto("toys");

        final ResultActions res = executePut(CATEGORY_BASE_URL + "/450baeb2-2908-4998-82ac-173c5b699df4", expectedRequest);
        checkForNoContent(res);

        final Optional<CategoryEntity> category = categoryRepository.findById(UUID.fromString("450baeb2-2908-4998-82ac-173c5b699df4"));
        Assertions.assertTrue(category.isPresent());
        Assertions.assertEquals("toys", category.get().getName());
    }

}
