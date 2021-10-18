package bym.shop.repository;

import bym.shop.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {

    Collection<OrderItemEntity> findAllByOrderIdAndDeletedIsNull(final UUID orderId);

    Optional<OrderItemEntity> findByIdAndDeletedIsNull(final UUID id);

    Collection<OrderItemEntity> findAllByDeletedIsNullAndProductId(final UUID productId);
}
