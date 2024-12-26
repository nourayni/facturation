package com.nourayni.facturation.productservice;

import com.nourayni.facturation.dtos.ProductRequest;
import com.nourayni.facturation.dtos.ProductResponse;

public interface ProductService {
    ProductResponse saveProduct(ProductRequest product);
    ProductResponse updateProduct(String productId);
    ProductResponse getProduct(String id);
    void deleteProduct(String id);



}
