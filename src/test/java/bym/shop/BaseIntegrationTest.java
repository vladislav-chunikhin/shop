package bym.shop;

import bym.shop.dto.RequestDto;
import bym.shop.repository.CategoryRepository;
import bym.shop.repository.ProductRepository;
import bym.shop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ShopApplication.class
)
public abstract class BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected ProductRepository productRepository;

    protected ObjectMapper mapper = new ObjectMapper();

    protected ResultActions executePost(final String url, final RequestDto request) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );
    }

    protected ResultActions executePut(final String url, final RequestDto request) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );
    }

    protected ResultActions executeGet(final String url) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    protected ResultActions executeDelete(final String url) throws Exception {
        return mockMvc.perform(
                MockMvcRequestBuilders
                        .delete(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
    }

    protected void checkForSuccess(final ResultActions result) throws Exception {
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    protected void checkForClientError(final ResultActions result) throws Exception {
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    protected void checkForNoContent(final ResultActions result) throws Exception {
        result.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    protected void checkForNotFound(final ResultActions result) throws Exception {
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    protected void checkForGone(final ResultActions result) throws Exception {
        result.andExpect(MockMvcResultMatchers.status().isGone());
    }

    protected void checkForInternalServerError(final ResultActions result) throws Exception {
        result.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

}
