package com.nourayni.facturation.dtos;


import com.nourayni.facturation.entity.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class LigneFacturationDTO {
    private Product product;
    private Integer quantity;
    private Double price;

}
