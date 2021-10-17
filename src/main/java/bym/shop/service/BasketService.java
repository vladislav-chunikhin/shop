package bym.shop.service;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.basket.request.BasketRequestDto;
import bym.shop.dto.basket.request.BasketUpdateRequestDto;
import bym.shop.dto.basket.request.OrderItemRequestDto;
import bym.shop.dto.basket.request.OrderItemUpdateRequestDto;
import bym.shop.dto.basket.response.BasketResponseDto;
import bym.shop.elasticsearch.OrderElasticSearchService;
import bym.shop.entity.OrderEntity;
import bym.shop.entity.OrderItemEntity;
import bym.shop.entity.ProductEntity;
import bym.shop.exception.ResourceDeletedException;
import bym.shop.repository.OrderItemRepository;
import bym.shop.repository.OrderRepository;
import bym.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderElasticSearchService orderElasticSearchService;

    @Transactional
    public CommonArrayResponseDto<BasketResponseDto> create(@NonNull final BasketRequestDto request) {
        final UUID orderId = UUID.fromString(request.getOrderId());
        final List<UUID> productIds = request.getItems().stream().map(OrderItemRequestDto::getProductId).map(UUID::fromString).collect(Collectors.toList());

        final Collection<ProductEntity> products = productRepository.findAllByDeletedIsNullAndIdIn(productIds);
        final Map<UUID, BigDecimal> productIdToPrice = products.stream().collect(Collectors.toMap(ProductEntity::getId, ProductEntity::getPrice));
        if (productIdToPrice.isEmpty()) throw new EntityNotFoundException("There are no products");

        final List<OrderItemEntity> orderItems = request.getItems().stream().map(it -> toEntity(orderId, it, productIdToPrice)).collect(Collectors.toList());

        final BigDecimal totalAmount = orderItems.stream().map(OrderItemEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        final OrderEntity order = orderRepository.findByIdAndDeletedIsNull(orderId).orElseThrow(() -> new EntityNotFoundException("Order is not found"));
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        orderElasticSearchService.saveOrder(orderId, products, order);

        return new CommonArrayResponseDto<>(orderItemRepository.saveAll(orderItems).stream().map(BasketResponseDto::from).collect(Collectors.toList()));
    }

    @Transactional
    public void update(
            @NonNull final UUID orderId,
            @NonNull final BasketUpdateRequestDto request
    ) {
        final List<UUID> productIds = request.getItems().stream().map(OrderItemUpdateRequestDto::getProductId).map(UUID::fromString).collect(Collectors.toList());

        final Collection<ProductEntity> products = productRepository.findAllByDeletedIsNullAndIdIn(productIds);
        final Map<UUID, BigDecimal> productIdToPrice = products.stream().collect(Collectors.toMap(ProductEntity::getId, ProductEntity::getPrice));
        if (productIdToPrice.isEmpty()) throw new EntityNotFoundException("There are no products");

        final Collection<OrderItemUpdateRequestDto> itemsFromClient = request.getItems();
        final Map<UUID, OrderItemUpdateRequestDto> itemIdToItemDto = itemsFromClient.stream().collect(Collectors.toMap(k -> UUID.fromString(k.getId()), Function.identity()));
        final Collection<OrderItemEntity> items = orderItemRepository.findAllByOrderIdAndDeletedIsNull(orderId);

        items.forEach(it -> {
                    final OrderItemUpdateRequestDto dto = itemIdToItemDto.get(it.getId());
                    it.setQuantity(dto.getQuantity());
                    final UUID productId = UUID.fromString(dto.getProductId());
                    final BigDecimal price = Optional.ofNullable(productIdToPrice.get(productId)).orElseThrow(() -> new RuntimeException("Price is missing"));
                    it.setProductId(productId);
                    it.setPrice(price);
                }
        );

        orderItemRepository.saveAll(items);

        final BigDecimal totalAmount = items.stream().map(OrderItemEntity::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        final OrderEntity order = orderRepository.findByIdAndDeletedIsNull(orderId).orElseThrow(() -> new EntityNotFoundException("Order is not found"));
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        orderElasticSearchService.updateOrder(orderId, products, order);
    }

    @Transactional
    public void delete(@NonNull final UUID orderId) {
        final OrderEntity order = orderRepository.findByIdAndDeletedIsNull(orderId).orElseThrow(() -> new EntityNotFoundException("Order is not found"));
        if (order.getDeleted() != null) throw new ResourceDeletedException("Order has already been deleted");
        final LocalDateTime deleted = LocalDateTime.now();
        order.setDeleted(deleted);
        orderRepository.save(order);

        final Collection<OrderItemEntity> items = orderItemRepository.findAllByOrderIdAndDeletedIsNull(orderId);
        if (!CollectionUtils.isEmpty(items)) {
            items.forEach(it -> it.setDeleted(deleted));
            orderItemRepository.saveAll(items);
        }

        orderElasticSearchService.delete(orderId);
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
