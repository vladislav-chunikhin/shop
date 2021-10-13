package bym.shop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Table(name = "orders")
@Entity
@Data
public class OrderEntity extends BaseEntity {

    @Column(name = "user_id")
    private UUID userId;

}
