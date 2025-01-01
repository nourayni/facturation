package com.nourayni.facturation.productservice;

import java.util.List;

import com.nourayni.facturation.dtos.ProductRequest;
import com.nourayni.facturation.dtos.ProductResponse;
import com.nourayni.facturation.utils.PaginatedResponse;

public interface ProductService {
    ProductResponse saveProduct(ProductRequest product);
    ProductResponse updateProduct(String productId, ProductRequest product);
    ProductResponse getProduct(String id);
    void deleteProduct(String id);
    List<ProductResponse> getAllProducts();
    PaginatedResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction);



}
