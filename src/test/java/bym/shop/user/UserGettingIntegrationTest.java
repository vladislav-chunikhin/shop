package bym.shop.user;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.UserController;
import bym.shop.dto.UserResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static bym.shop.constants.BaseURL.USER_BASE_URL;

/**
 * Integration tests for {@link UserController#get(String)}.
 */
public class UserGettingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/insert-user.sql")
    @SqlAfter("/sql/delete-user.sql")
    @Test
    public void userGettingInTheUsualCase() throws Exception {
        final ResultActions res = executeGet(USER_BASE_URL + "/f7ef3015-1215-432a-a055-34033f01de59");
        checkForSuccess(res);

        final UserResponseDto response = mapper.readValue(res.andReturn().getResponse().getContentAsString(), UserResponseDto.class);
        Assertions.assertEquals("Vladislav Chunikhin", response.getFullName());
        Assertions.assertNotNull(response.getId());
    }

}
