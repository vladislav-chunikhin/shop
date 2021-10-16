package bym.shop.repository;

import bym.shop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    Collection<ProductEntity> findAllByCategoryIdAndDeletedIsNull(final UUID categoryId);

    Optional<ProductEntity> findByIdAndDeletedIsNull(final UUID id);

    Collection<ProductEntity> findAllByDeletedIsNullAndIdIn(final Collection<UUID> ids);

    Collection<ProductEntity> findAllByDeletedIsNull();

}
