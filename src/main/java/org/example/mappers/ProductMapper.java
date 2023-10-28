package org.example.mappers;

import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.entities.CategoryEntity;
import org.example.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductItemDTO productToItemDTO(ProductEntity product);
    @Mapping(target = "categoryId", source = "category.id")
    List<ProductItemDTO> listProductToItemDTO(List<ProductEntity> product);
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "categoryIdToCategory")
    ProductEntity productByCreateProductDTO(ProductCreateDTO dto);



    @Named("categoryIdToCategory")
    static CategoryEntity categoryIdToCategory(int categoryId) {
        CategoryEntity category = CategoryEntity
                .builder()
                .id(categoryId)
                .build();

        return category;
    }
}
