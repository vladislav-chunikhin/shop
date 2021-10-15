package bym.shop.repository;

import bym.shop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    Collection<ProductEntity> findAllByCategoryIdAndDeletedIsNull(final UUID categoryId);

}
