package bym.shop.elasticsearch;

import bym.shop.elasticsearch.repository.OrderElasticSearchRepository;
import bym.shop.entity.OrderEntity;
import bym.shop.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderElasticSearchService {

    private final OrderElasticSearchRepository orderElasticSearchRepository;

    public void saveOrder(
            @NonNull final UUID orderId,
            @NonNull final Collection<ProductEntity> products,
            @NonNull final OrderEntity order
    ) {
        final Order orderInfo = new Order();
        orderInfo.setId(UUID.randomUUID());
        orderInfo.setOrderId(orderId);
        orderInfo.setUserId(order.getUserId());
        orderInfo.setProducts(products.stream().map(ProductEntity::getName).collect(Collectors.toSet()));
        orderInfo.setTotalAmount(order.getTotalAmount());
        orderElasticSearchRepository.save(orderInfo);
    }

    public void updateOrder(
            @NonNull final UUID orderId,
            @NonNull final Collection<ProductEntity> products,
            @NonNull final OrderEntity orderEntity
    ) {
        final Optional<Order> order = orderElasticSearchRepository.findByOrderId(orderId);
        if (order.isPresent()) {
            order.get().setProducts(products.stream().map(ProductEntity::getName).collect(Collectors.toSet()));
            order.get().setTotalAmount(orderEntity.getTotalAmount());
            order.get().setUserId(orderEntity.getUserId());
            orderElasticSearchRepository.save(order.get());
        } else {
            saveOrder(orderId, products, orderEntity);
        }
    }

    public void updateOrders(
            @NonNull final Collection<UUID> orderIds,
            @NonNull final String oldProductName, 
            @NonNull final String newProductName
    ) {
        final Collection<Order> orders = orderElasticSearchRepository.findAllByOrderIdIn(orderIds);
        if (!CollectionUtils.isEmpty(orders)) {
            orders.forEach(it -> {
                final Set<String> products = it.getProducts();
                products.remove(oldProductName);
                products.add(newProductName);
                it.setProducts(products);
            });
            orderElasticSearchRepository.saveAll(orders);
        }
    }

    public void delete(@NonNull final UUID orderId) {
        orderElasticSearchRepository.deleteByOrderId(orderId);
    }
}
