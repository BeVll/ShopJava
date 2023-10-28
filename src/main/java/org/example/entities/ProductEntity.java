package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "tbl_products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name", length = 250, nullable = false)
    private String name;
    @Column(name="description", length = 250, nullable = false)
    private String description;
    @Column(name="price_discount", nullable = false)
    private float price_discount;
    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private CategoryEntity category;
    @Transient
    private int categoryId;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> images;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BasketEntity> baskets;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderItemEntity> orderItems;

}
