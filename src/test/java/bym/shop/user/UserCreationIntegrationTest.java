package bym.shop.user;

import bym.shop.BaseIntegrationTest;
import bym.shop.dto.UserRequestDto;
import bym.shop.dto.UserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static bym.shop.constants.BaseURL.USER_BASE_URL;

public class UserCreationIntegrationTest extends BaseIntegrationTest {

    @Test
    public void userCreationInTheUsualCase() throws Exception {
        final UserRequestDto expectedRequest = new UserRequestDto("Alex Wood");

        final ResultActions res = executePost(USER_BASE_URL, expectedRequest);

        checkForSuccess(res);
        UserResponseDto response = mapper.readValue(res.andReturn().getResponse().getContentAsString(), UserResponseDto.class);
        Assertions.assertEquals(expectedRequest.getFullName(), response.getFullName());
        Assertions.assertNotNull(response.getId());
    }

}
