package bym.shop.user;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.UserController;
import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.user.UserResponseDto;
import bym.shop.exception.ErrorResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static bym.shop.constants.BaseURL.USER_BASE_URL;

/**
 * Integration tests for {@link UserController#get(Collection)}.
 */
public class UserGettingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/user/insert-user.sql")
    @SqlAfter("/sql/user/delete-user.sql")
    @Test
    public void userGettingInTheUsualCase() throws Exception {
        final ResultActions res = executeGet(USER_BASE_URL + "?ids=f7ef3015-1215-432a-a055-34033f01de59, c41ac77a-2449-43d5-818c-d00fd03668ba");
        checkForSuccess(res);

        final CommonArrayResponseDto<UserResponseDto> response = mapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(2, response.getData().size());
        final List<String> expectedNames = Arrays.asList("Vladislav Chunikhin", "Maxim Azarov");
        response.getData().forEach(it -> Assertions.assertTrue(expectedNames.contains(it.getFullName())));
    }

    @SqlBefore("/sql/user/insert-user.sql")
    @SqlAfter("/sql/user/delete-user.sql")
    @Test
    public void userGettingWhenIdsAreMissing() throws Exception {
        final ResultActions res = executeGet(USER_BASE_URL);
        checkForSuccess(res);
        final CommonArrayResponseDto<UserResponseDto> response = mapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(3, response.getData().size());
        final List<String> expectedNames = Arrays.asList("Vladislav Chunikhin", "Maxim Azarov", "Zera Wood");
        response.getData().forEach(it -> Assertions.assertTrue(expectedNames.contains(it.getFullName())));
    }

    @Test
    public void userGettingWithMalformedId() throws Exception {
        final ResultActions res = executeGet(USER_BASE_URL + "?ids=bad-uuid");
        checkForInternalServerError(res);

        final ErrorResponseDto response = mapper.readValue(res.andReturn().getResponse().getContentAsString(), ErrorResponseDto.class);
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertEquals("Invalid UUID string: bad-uuid", response.getMessage());
    }

    @SqlBefore("/sql/user/insert-user.sql")
    @SqlAfter("/sql/user/delete-user.sql")
    @Test
    public void userGettingByNotExistingIds() throws Exception {
        final ResultActions res = executeGet(USER_BASE_URL + "?ids=aa87726f-c86b-451a-b500-4107504c7ccf");
        checkForSuccess(res);
        final CommonArrayResponseDto<UserResponseDto> response = mapper.readValue(
                res.andReturn().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        Assertions.assertEquals(0, response.getData().size());
    }

}
