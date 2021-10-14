package bym.shop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Doc configuration.
 * Start page: http://{host}:{port}/swagger-ui.html
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Amazon shop",
                contact = @Contact(
                        name = "Vladislav Chunikhin",
                        url = "https://www.facebook.com/vladislav.chunikhin.12",
                        email = "vladislav.chunikhin.95@gmail.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "local server")
        }
)
@Configuration
public class ApiDocumentationConfig {
}
