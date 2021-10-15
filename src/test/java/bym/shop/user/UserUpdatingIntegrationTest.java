package bym.shop.user;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.UserController;
import bym.shop.dto.user.UserRequestDto;
import bym.shop.entity.UserEntity;
import bym.shop.exception.ErrorResponseDto;
import bym.shop.util.SqlAfter;
import bym.shop.util.SqlBefore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

import static bym.shop.constants.BaseURL.USER_BASE_URL;

/**
 * Integration tests for {@link UserController#update(UserRequestDto, String)}.
 */
public class UserUpdatingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/user/insert-user.sql")
    @SqlAfter("/sql/user/delete-user.sql")
    @Test
    public void userUpdatingInTheUsualCase() throws Exception {
        final UserRequestDto expectedRequest = new UserRequestDto("Alex Wood");

        final ResultActions res = executePut(USER_BASE_URL + "/f7ef3015-1215-432a-a055-34033f01de59", expectedRequest);
        checkForNoContent(res);

        final Optional<UserEntity> user = userRepository.findById(UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"));
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals("Alex Wood", user.get().getFullName());
    }

    @Test
    public void userUpdatingWhenUserIsMissing() throws Exception {
        final UserRequestDto expectedRequest = new UserRequestDto("Alex Wood");

        final ResultActions res = executePut(USER_BASE_URL + "/4d4b6161-dd5f-410a-83cc-41b247452f3e", expectedRequest);
        checkForNotFound(res);

        final ErrorResponseDto response = mapper.readValue(res.andReturn().getResponse().getContentAsString(), ErrorResponseDto.class);
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertEquals("User is not found", response.getMessage());
    }
}
