package bym.shop.service;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.basket.BasketRequestDto;
import bym.shop.dto.basket.BasketResponseDto;
import bym.shop.dto.basket.OrderItemRequestDto;
import bym.shop.entity.OrderEntity;
import bym.shop.entity.OrderItemEntity;
import bym.shop.entity.ProductEntity;
import bym.shop.repository.OrderItemRepository;
import bym.shop.repository.OrderRepository;
import bym.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CommonArrayResponseDto<BasketResponseDto> create(@NonNull final BasketRequestDto request) {
        final UUID orderId = UUID.fromString(request.getOrderId());
        final List<UUID> productIds = request.getItems().stream().map(OrderItemRequestDto::getProductId).map(UUID::fromString).collect(Collectors.toList());

        final Map<UUID, BigDecimal> productIdToPrice = productRepository
                .findAllByDeletedIsNullAndIdIn(productIds)
                .stream()
                .collect(Collectors.toMap(ProductEntity::getId, ProductEntity::getPrice));
        if (productIdToPrice.isEmpty()) throw new EntityNotFoundException("There are no products");

        final List<OrderItemEntity> orderItems = request.getItems().stream().map(it -> toEntity(orderId, it, productIdToPrice)).collect(Collectors.toList());

        final BigDecimal totalAmount = orderItems.stream().map(OrderItemEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        final OrderEntity order = orderRepository.findByIdAndDeletedIsNull(orderId).orElseThrow(() -> new EntityNotFoundException("Order is not found"));
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        //todo save to elasticsearch

        return new CommonArrayResponseDto<>(orderItemRepository.saveAll(orderItems).stream().map(BasketResponseDto::from).collect(Collectors.toList()));
    }

    private OrderItemEntity toEntity(
            @NonNull final UUID orderId,
            @NonNull final OrderItemRequestDto orderItemDto,
            @NonNull final Map<UUID, BigDecimal> productIdToPrice
    ) {
        final OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrderId(orderId);
        final UUID productId = UUID.fromString(orderItemDto.getProductId());
        orderItem.setProductId(productId);
        orderItem.setQuantity(orderItemDto.getQuantity());
        final BigDecimal price = Optional.ofNullable(productIdToPrice.get(productId)).orElseThrow(() -> new RuntimeException("Price is missing"));
        orderItem.setPrice(price.multiply(BigDecimal.valueOf(orderItemDto.getQuantity())));
        return orderItem;
    }
}
