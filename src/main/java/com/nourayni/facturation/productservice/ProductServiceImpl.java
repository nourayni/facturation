package com.nourayni.facturation.productservice;

import org.springframework.stereotype.Service;

import com.nourayni.facturation.dtos.ProductRequest;
import com.nourayni.facturation.dtos.ProductResponse;
import com.nourayni.facturation.entity.Product;
import com.nourayni.facturation.exception.ProductAlreadyExistException;
import com.nourayni.facturation.exception.ProductNotFoundException;
import com.nourayni.facturation.mapper.ProductMapper;
import com.nourayni.facturation.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) {
        Product product = productRepository.findByNomProduct(productRequest.getNomProduct()).get();
        if (product == null) {
            product = new Product();
            product.setNomProduct(productRequest.getNomProduct());
            product.setPrice(productRequest.getPrice());
            productRepository.save(product);
            return productMapper.toProductResponse(product);
            
        }
        throw new ProductAlreadyExistException("ce produit existe déjà");
    }

    @Override
    public ProductResponse updateProduct(String  productId) {
        Product product = productRepository.findById(productId).get();
        if (product != null) {
            product.setNomProduct(product.getNomProduct());
            product.setPrice(product.getPrice());
            productRepository.save(product);
            return productMapper.toProductResponse(product);
        }
        throw new ProductNotFoundException("ce produit n'existe pas");
    }

    @Override
    public ProductResponse getProduct(String id) {
        return productMapper.toProductResponse(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("ce produit n'existe pas")));
    }

    @Override
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("ce produit n'existe pas"));
        productRepository.delete(product);
    }
    

}
