package com.nourayni.facturation.mapper;

import org.springframework.stereotype.Service;

import com.nourayni.facturation.dtos.ProductResponse;
import com.nourayni.facturation.entity.Product;

@Service
public class ProductMapper {

    public ProductResponse toProductResponse(Product product) {
        if (product != null) {
            return ProductResponse.builder()
                .id(product.getId())
                .nomProduct(product.getNomProduct())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .updateAt(product.getUpdateAt())
            .build();
        }
        return null;
    }

}
