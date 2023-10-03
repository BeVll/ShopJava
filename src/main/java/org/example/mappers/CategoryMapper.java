package org.example.mappers;

import org.example.dto.category.CategoryModelDTO;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryModelDTO categoryToModelDTO(CategoryEntity category);
    List<CategoryModelDTO> listCategoriesToModelDto(List<CategoryEntity> list);
}
