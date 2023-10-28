package org.example.dto.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductCreateDTO {
    private String name;
    private String description;
    private float price_discount;
    private List<MultipartFile> images;
    private int categoryId;
}
