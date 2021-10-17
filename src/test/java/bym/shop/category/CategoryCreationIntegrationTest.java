package bym.shop.category;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.CategoryController;
import bym.shop.dto.category.CategoryRequestDto;
import bym.shop.dto.category.CategoryResponseDto;
import bym.shop.util.SqlAfter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static bym.shop.constants.BaseURL.CATEGORY_BASE_URL;

/**
 * Integration tests for {@link CategoryController#create(CategoryRequestDto)}.
 */
public class CategoryCreationIntegrationTest extends BaseIntegrationTest {

    @SqlAfter("/sql/category/delete-category.sql")
    @Test
    public void categoryCreationInTheUsualCase() throws Exception {
        final CategoryRequestDto expectedRequest = new CategoryRequestDto("Cars");

        final ResultActions res = executePost(CATEGORY_BASE_URL, expectedRequest);
        checkForSuccess(res);

        final CategoryResponseDto response = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), CategoryResponseDto.class);
        Assertions.assertEquals(expectedRequest.getName(), response.getName());
        Assertions.assertNotNull(response.getId());
    }

}
