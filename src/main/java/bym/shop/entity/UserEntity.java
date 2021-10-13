package bym.shop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
@Entity
@Data
public class UserEntity extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;

}
