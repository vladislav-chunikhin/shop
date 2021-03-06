package bym.shop.elasticsearch.repository;

import bym.shop.elasticsearch.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OrderElasticSearchRepository extends ElasticsearchRepository<Order, UUID> {

    Collection<Order> findAllByProductsContaining(final String productName);

    Optional<Order> findByOrderId(final UUID orderId);

    Collection<Order> findAllByOrderIdIn(final Collection<UUID> orderIds);

    void deleteByOrderId(final UUID orderId);

}
