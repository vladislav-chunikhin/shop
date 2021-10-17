package bym.shop.service;

import bym.shop.dto.CommonArrayResponseDto;
import bym.shop.dto.order.OrderRequestDto;
import bym.shop.dto.order.OrderResponseDto;
import bym.shop.elasticsearch.OrderInfo;
import bym.shop.elasticsearch.repository.OrderInfoElasticSearchRepository;
import bym.shop.entity.OrderEntity;
import bym.shop.entity.OrderItemEntity;
import bym.shop.exception.ResourceDeletedException;
import bym.shop.repository.OrderItemRepository;
import bym.shop.repository.OrderRepository;
import bym.shop.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderInfoElasticSearchRepository orderInfoElasticSearchRepository;

    public OrderResponseDto create(@NonNull final OrderRequestDto request) {
        final OrderEntity order = new OrderEntity();
        order.setUserId(UUID.fromString(request.getUserId()));
        order.setTotalAmount(BigDecimal.ZERO);
        return OrderResponseDto.from(orderRepository.save(order));
    }

    public void update(@NonNull final UUID id, @NonNull final OrderRequestDto request) {
        final OrderEntity order = orderRepository.findByIdAndDeletedIsNull(id).orElseThrow(() -> new EntityNotFoundException("Order is not found"));
        order.setUserId(UUID.fromString(request.getUserId()));
        orderRepository.save(order);
    }

    public CommonArrayResponseDto<OrderResponseDto> get(
            @Nullable final Collection<String> ids,
            @Nullable final String product
    ) {
        if (StringUtils.isEmpty(product)) {
            return CommonUtil.getByIds(ids, OrderResponseDto::from, orderRepository::findAllByDeletedIsNull, orderRepository::findAllByDeletedIsNullAndIdIn);
        }

        final Collection<OrderInfo> orders = orderInfoElasticSearchRepository.findAllByProductsContaining(product);
        return new CommonArrayResponseDto<>(orders.stream().map(OrderResponseDto::from).collect(Collectors.toList()));
    }

    public void delete(@NonNull final UUID id) {
        final OrderEntity order = orderRepository.findByIdAndDeletedIsNull(id).orElseThrow(() -> new EntityNotFoundException("Order is not found"));
        if (order.getDeleted() != null) throw new ResourceDeletedException("Order has already been deleted");
        order.setDeleted(LocalDateTime.now());
        orderRepository.save(order);

        final Collection<OrderItemEntity> items = orderItemRepository.findAllByOrderIdAndDeletedIsNull(id);
        if (!CollectionUtils.isEmpty(items)) {
            items.forEach(it -> it.setDeleted(LocalDateTime.now()));
            orderItemRepository.saveAll(items);
        }
    }
}
