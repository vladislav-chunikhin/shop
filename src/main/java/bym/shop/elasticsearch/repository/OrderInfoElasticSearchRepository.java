package bym.shop.elasticsearch.repository;

import bym.shop.elasticsearch.OrderInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;
import java.util.UUID;

public interface OrderInfoElasticSearchRepository extends ElasticsearchRepository<OrderInfo, UUID> {

    Collection<OrderInfo> findAllByProductsContaining(final String productName);

}
