package bym.shop.user;

import bym.shop.BaseIntegrationTest;
import bym.shop.dto.UserRequestDto;
import bym.shop.dto.UserResponseDto;
import bym.shop.exception.ErrorResponse;
import bym.shop.util.SqlAfter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import bym.shop.controller.UserController;

import static bym.shop.constants.BaseURL.USER_BASE_URL;

/**
 * Integration tests for {@link UserController#create(UserRequestDto)}.
 */
public class UserCreationIntegrationTest extends BaseIntegrationTest {

    @SqlAfter("/sql/delete-user.sql")
    @Test
    public void userCreationInTheUsualCase() throws Exception {
        final UserRequestDto expectedRequest = new UserRequestDto("Alex Wood");

        final ResultActions res = executePost(USER_BASE_URL, expectedRequest);
        checkForSuccess(res);

        final UserResponseDto response = mapper.readValue(res.andReturn().getResponse().getContentAsString(), UserResponseDto.class);
        Assertions.assertEquals(expectedRequest.getFullName(), response.getFullName());
        Assertions.assertNotNull(response.getId());
    }

    @Test
    public void userCreationWhenFullNameIsNull() throws Exception {
        final UserRequestDto expectedRequest = new UserRequestDto();
        checkInvalidParameters(expectedRequest);
    }

    @Test
    public void userCreationWhenFullNameIsBlank() throws Exception {
        final UserRequestDto expectedRequest = new UserRequestDto("");
        checkInvalidParameters(expectedRequest);
    }

    private void checkInvalidParameters(final UserRequestDto expectedRequest) throws Exception {
        final ResultActions res = executePost(USER_BASE_URL, expectedRequest);
        checkForClientError(res);
        final ErrorResponse response = mapper.readValue(res.andReturn().getResponse().getContentAsString(), ErrorResponse.class);
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertEquals("Invalid input parameters. \n" +
                "Validation failed for field: fullName , object: userRequestDto. Message: must not be blank", response.getMessage());
    }

}
