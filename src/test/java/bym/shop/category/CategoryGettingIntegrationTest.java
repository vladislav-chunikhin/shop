package bym.shop.category;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.CategoryController;
import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.category.CategoryResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static bym.shop.constants.BaseURL.CATEGORY_BASE_URL;

/**
 * Integration tests for {@link CategoryController#get(Collection)}.
 */
public class CategoryGettingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/category/insert-category.sql")
    @SqlAfter("/sql/category/delete-category.sql")
    @Test
    public void categoryGettingInTheUsualCase() throws Exception {
        final ResultActions res = executeGet(CATEGORY_BASE_URL + "?ids=450baeb2-2908-4998-82ac-173c5b699df4,19184ac3-014c-4c83-89b1-879bd5f583e2");
        checkForSuccess(res);

        final CommonArrayResponseDto<CategoryResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(2, response.getData().size());
        final List<String> expectedNames = Arrays.asList("cars", "furniture");
        response.getData().forEach(it -> Assertions.assertTrue(expectedNames.contains(it.getName())));
    }

    @SqlBefore("/sql/category/insert-category.sql")
    @SqlAfter("/sql/category/delete-category.sql")
    @Test
    public void categoryGettingWhenIdsAreMissing() throws Exception {
        final ResultActions res = executeGet(CATEGORY_BASE_URL);
        checkForSuccess(res);
        final CommonArrayResponseDto<CategoryResponseDto> response = objectMapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(3, response.getData().size());
        final List<String> expectedNames = Arrays.asList("cars", "furniture", "toys");
        response.getData().forEach(it -> Assertions.assertTrue(expectedNames.contains(it.getName())));
    }
}
