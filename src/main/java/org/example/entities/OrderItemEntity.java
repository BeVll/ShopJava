package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="tblOrderItems")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    ///ціна по якій куплено товар
    @Column(name="price_buy", nullable = false)
    private double priceBuy;

    @Column(name="item_count", nullable = false)
    private short count;

    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private ProductEntity product;

    @Transient
    private int productId;

    @ManyToOne
    @JoinColumn(name="order_id", nullable = false)
    private OrderEntity order;

    @Transient
    private int orderId;
}
