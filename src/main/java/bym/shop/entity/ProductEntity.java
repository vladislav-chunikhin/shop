package bym.shop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Table(name = "tables")
@Entity
@Data
public class ProductEntity extends BaseEntity {

    private String name;

    private BigDecimal price;

    private String sku;

    @Column(name = "category_id")
    private UUID categoryId;

}

