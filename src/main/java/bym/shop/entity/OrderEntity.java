package bym.shop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Table(name = "orders")
@Entity
@Data
public class OrderEntity extends BaseEntity {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

}
