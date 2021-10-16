package bym.shop.product;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.ProductController;
import bym.shop.dto.product.ProductRequestDto;
import bym.shop.dto.product.ProductResponseDto;
import bym.shop.exception.ErrorResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static bym.shop.constants.BaseURL.PRODUCT_BASE_URL;

/**
 * Integration tests for {@link ProductController#create(ProductRequestDto)}.
 */
public class ProductCreationIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/product/insert-category.sql")
    @SqlAfter("/sql/product/delete-product.sql")
    @Test
    public void productCreationInTheUsualCase() throws Exception {
        final ProductRequestDto expectedRequest = new ProductRequestDto(
                "cupboard",
                BigDecimal.valueOf(24.55),
                "UGG-BB-PUR-06",
                "19184ac3-014c-4c83-89b1-879bd5f583e2"
        );

        final ResultActions res = executePost(PRODUCT_BASE_URL, expectedRequest);
        checkForSuccess(res);

        final ProductResponseDto response = mapper.readValue(res.andReturn().getResponse().getContentAsString(), ProductResponseDto.class);
        Assertions.assertEquals(expectedRequest.getName(), response.getName());
        Assertions.assertEquals(expectedRequest.getCategoryId(), response.getCategoryId().toString());
        Assertions.assertEquals(expectedRequest.getPrice(), response.getPrice());
        Assertions.assertEquals(expectedRequest.getSku(), response.getSku());
        Assertions.assertNotNull(response.getId());
    }

    @Test
    public void productCreationWhenNameIsBlank() throws Exception {
        final ProductRequestDto expectedRequest = new ProductRequestDto(
                "",
                BigDecimal.valueOf(24.55),
                "UGG-BB-PUR-06",
                "19184ac3-014c-4c83-89b1-879bd5f583e2"
        );
        checkInvalidParameters(expectedRequest, "Invalid input parameters. \n" +
                "Validation failed for field: name , object: productRequestDto. Message: must not be blank");
    }

    @Test
    public void productCreationWhenSKUIsBlank() throws Exception {
        final ProductRequestDto expectedRequest = new ProductRequestDto(
                "cupboard",
                BigDecimal.valueOf(24.55),
                "",
                "19184ac3-014c-4c83-89b1-879bd5f583e2"
        );
        checkInvalidParameters(expectedRequest, "Invalid input parameters. \n" +
                "Validation failed for field: sku , object: productRequestDto. Message: must not be blank");
    }

    @Test
    public void productCreationWhenCategoryIdIsBlank() throws Exception {
        final ProductRequestDto expectedRequest = new ProductRequestDto(
                "cupboard",
                BigDecimal.valueOf(24.55),
                "UGG-BB-PUR-06",
                ""
        );
        checkInvalidParameters(expectedRequest, "Invalid input parameters. \n" +
                "Validation failed for field: categoryId , object: productRequestDto. Message: must not be blank");
    }

    @Test
    public void productCreationWhenPrizeEqualsZero() throws Exception {
        final ProductRequestDto expectedRequest = new ProductRequestDto(
                "cupboard",
                BigDecimal.valueOf(0),
                "UGG-BB-PUR-06",
                "19184ac3-014c-4c83-89b1-879bd5f583e2"
        );
        checkInvalidParameters(expectedRequest, "Invalid input parameters. \n" +
                "Validation failed for field: price , object: productRequestDto. Message: must be greater than 0.0");
    }

    @Test
    public void productCreationWhenPrizeEqualsZeroWithPoint() throws Exception {
        final ProductRequestDto expectedRequest = new ProductRequestDto(
                "cupboard",
                BigDecimal.valueOf(0.0),
                "UGG-BB-PUR-06",
                "19184ac3-014c-4c83-89b1-879bd5f583e2"
        );
        checkInvalidParameters(expectedRequest, "Invalid input parameters. \n" +
                "Validation failed for field: price , object: productRequestDto. Message: must be greater than 0.0");
    }

    @Test
    public void productCreationWhenCategoryIdIsInvalid() throws Exception {
        final ProductRequestDto expectedRequest = new ProductRequestDto(
                "cupboard",
                BigDecimal.valueOf(24.55),
                "UGG-BB-PUR-06",
                "7da45664-2a24-4439-8ac3-322fd98497b0"
        );
        final ResultActions res = executePost(PRODUCT_BASE_URL, expectedRequest);
        checkForInternalServerError(res);
        final ErrorResponseDto response = mapper.readValue(res.andReturn().getResponse().getContentAsString(), ErrorResponseDto.class);
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertEquals("could not execute statement; SQL [n/a]; constraint [fk_products_to_categories]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement", response.getMessage());
    }

    private void checkInvalidParameters(final ProductRequestDto expectedRequest, final String expectedErrorMessage) throws Exception {
        final ResultActions res = executePost(PRODUCT_BASE_URL, expectedRequest);
        checkForClientError(res);
        final ErrorResponseDto response = mapper.readValue(res.andReturn().getResponse().getContentAsString(), ErrorResponseDto.class);
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertEquals(expectedErrorMessage, response.getMessage());
    }

}
