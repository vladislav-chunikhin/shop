package bym.shop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "categories")
@Entity
@Data
public class CategoryEntity extends BaseEntity {

    private String name;

}
