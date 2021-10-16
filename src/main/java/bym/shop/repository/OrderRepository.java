package bym.shop.repository;

import bym.shop.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByIdAndDeletedIsNull(final UUID id);

    Collection<OrderEntity> findAllByDeletedIsNullAndIdIn(final Collection<UUID> ids);

    Collection<OrderEntity> findAllByDeletedIsNull();
}
