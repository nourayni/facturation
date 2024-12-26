package com.nourayni.facturation.dtos;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class ProductResponse {
    private String id;
    private String nomProduct;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
