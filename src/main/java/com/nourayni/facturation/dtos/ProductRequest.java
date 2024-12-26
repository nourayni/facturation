package com.nourayni.facturation.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductRequest {
    private String nomProduct;
    private double price;

}
