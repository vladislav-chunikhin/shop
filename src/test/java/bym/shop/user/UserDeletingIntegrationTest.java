package bym.shop.user;

import bym.shop.BaseIntegrationTest;
import bym.shop.controller.UserController;
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
 * Integration tests for {@link UserController#delete(String)}.
 */
public class UserDeletingIntegrationTest extends BaseIntegrationTest {

    @SqlBefore("/sql/user/insert-user.sql")
    @SqlAfter("/sql/user/delete-user.sql")
    @Test
    public void userDeletingInTheUsualCase() throws Exception {
        final ResultActions res = executeDelete(USER_BASE_URL + "/f7ef3015-1215-432a-a055-34033f01de59");
        checkForNoContent(res);

        final Optional<UserEntity> user = userRepository.findById(UUID.fromString("f7ef3015-1215-432a-a055-34033f01de59"));
        Assertions.assertTrue(user.isPresent());
        Assertions.assertNotNull(user.get().getDeleted());
    }

    @SqlBefore("/sql/user/insert-deleted-user.sql")
    @SqlAfter("/sql/user/delete-user.sql")
    @Test
    public void userDeletingWhenUserHasAlreadyBeenDeleted() throws Exception {
        final ResultActions res = executeDelete(USER_BASE_URL + "/f7ef3015-1215-432a-a055-34033f01de59");
        checkForGone(res);

        final ErrorResponseDto response = objectMapper.readValue(res.andReturn().getResponse().getContentAsString(), ErrorResponseDto.class);
        Assertions.assertNotNull(response.getMessage());
        Assertions.assertEquals("User has already been deleted", response.getMessage());
    }

}
