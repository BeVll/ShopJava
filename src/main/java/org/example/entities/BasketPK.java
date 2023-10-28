package org.example.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasketPK implements Serializable {
    private UserEntity user;
    private ProductEntity product;
}
