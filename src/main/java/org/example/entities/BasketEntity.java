package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_basket")
@IdClass(BasketPK.class)
public class BasketEntity {
    @Id
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;
    @Id
    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private ProductEntity product;
    private short count;
}
