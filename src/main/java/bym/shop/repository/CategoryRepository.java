package bym.shop.repository;

import bym.shop.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByIdAndDeletedIsNull(final UUID id);

    Collection<CategoryEntity> findAllByDeletedIsNullAndIdIn(final Collection<UUID> ids);

    Collection<CategoryEntity> findAllByDeletedIsNull();

}
