package bym.shop.repository;

import bym.shop.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByIdAndDeletedIsNull(final UUID id);

    Collection<UserEntity> findAllByDeletedIsNullAndIdIn(final Collection<UUID> ids);

    Collection<UserEntity> findAllByDeletedIsNull();

}
