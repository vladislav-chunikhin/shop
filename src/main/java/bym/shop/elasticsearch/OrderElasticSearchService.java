package bym.shop.elasticsearch;

import bym.shop.elasticsearch.repository.OrderElasticSearchRepository;
import bym.shop.entity.OrderEntity;
import bym.shop.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderElasticSearchService {

    private final OrderElasticSearchRepository orderElasticSearchRepository;

    public void saveOrderToElasticSearch(
            @NonNull final UUID orderId,
            @NonNull final Collection<ProductEntity> products,
            @NonNull final OrderEntity order
    ) {
        final Order orderInfo = new Order();
        orderInfo.setId(UUID.randomUUID());
        orderInfo.setOrderId(orderId);
        orderInfo.setUserId(order.getUserId());
        orderInfo.setProducts(products.stream().map(ProductEntity::getName).collect(Collectors.toList()));
        orderInfo.setTotalAmount(order.getTotalAmount());
        orderElasticSearchRepository.save(orderInfo);
    }

}
