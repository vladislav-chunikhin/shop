package bym.shop.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableElasticsearchRepositories(basePackages = "bym.shop.elasticsearch.repository")
@Profile("test")
public class ElasticsearchTestConfig {

    @Bean
    public RestHighLevelClient client() {
        final Map<String, String> env = new HashMap<>();
        env.put("bootstrap.memory_lock", "true");
        env.put("discovery.type", "single-node");
        env.put("ES_JAVA_OPTS", "-Xms512m -Xmx512m");

        final ElasticsearchContainer elasticsearchContainer = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.14.1")
                .withEnv(env);
        elasticsearchContainer.start();

        final ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo(elasticsearchContainer.getHttpHostAddress())
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }

}
