package org.example.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductUpdateDTO;
import org.example.entities.ProductEntity;
import org.example.entities.ProductImageEntity;
import org.example.mappers.ProductMapper;
import org.example.repositories.ProductImageRepository;
import org.example.repositories.ProductRepository;
import org.example.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/products")
@SecurityRequirement(name="my-api")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductMapper productMapper;
    private final StorageService storageService;

    @GetMapping
    public  ResponseEntity<List<ProductItemDTO>> getAll(){
        var result = productMapper.listProductToItemDTO(productRepository.findAll());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductEntity> create(@ModelAttribute ProductCreateDTO dto) {
        ProductEntity product = productMapper.productByCreateProductDTO(dto);
        product.setImages(new ArrayList<>());
        productRepository.save(product);
        for(MultipartFile image : dto.getImages()) {
            String fileName = storageService.saveImage(image);
            ProductImageEntity pi = ProductImageEntity.builder()
                    .product(product)
                    .image(fileName)
                    .build();
            productImageRepository.save(pi);
            product.getImages().add(pi);
        }
        return ResponseEntity.ok().body(product);
    }

    @PostMapping(value="{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductEntity> update(@PathVariable int id, @ModelAttribute ProductUpdateDTO dto) {
        Optional<ProductEntity> productOpt = productRepository.findById(id);
        if(productOpt.isPresent())
        {
            var product = productOpt.get();
            if(dto.getImages()!=null) {
                if (product.getImages() != null) {
                    for (ProductImageEntity image : product.getImages()) {
                        storageService.removeFile(image.getImage());
                    }
                    for (MultipartFile image : dto.getImages()) {
                        String fileName = storageService.saveImage(image);
                        ProductImageEntity pi = ProductImageEntity.builder()
                                .product(product)
                                .image(fileName)
                                .build();
                        productImageRepository.save(pi);
                        product.getImages().add(pi);
                    }
                }
            }
            product.setName(dto.getName());
            product.setPrice_discount(dto.getPrice_discount());
            product.setCategoryId(dto.getCategoryId());
            product.setDescription(dto.getDescription());
            productRepository.save(product);
            var result = productMapper.productToItemDTO(product);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable int id) {
        Optional<ProductEntity> productOpt = productRepository.findById(id);
        if(productOpt.isPresent())
        {
            var product = productOpt.get();
            if(product.getImages()!=null) {
                for (ProductImageEntity image : product.getImages()) {
                    storageService.removeFile(image.getImage());
                }
            }
            productRepository.delete(product);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
