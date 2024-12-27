package com.nourayni.facturation.productservice;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nourayni.facturation.dtos.ProductRequest;
import com.nourayni.facturation.dtos.ProductResponse;
import com.nourayni.facturation.entity.Product;
import com.nourayni.facturation.exception.ProductAlreadyExistException;
import com.nourayni.facturation.exception.ProductNotFoundException;
import com.nourayni.facturation.mapper.ProductMapper;
import com.nourayni.facturation.repository.ProductRepository;
import com.nourayni.facturation.utils.PaginatedResponse;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) {
        Optional<Product> product = productRepository.findByNomProduct(productRequest.getNomProduct());
        if (product.isEmpty()) {
            Product newProduct = new Product();
            newProduct.setNomProduct(productRequest.getNomProduct());
            newProduct.setPrice(productRequest.getPrice());
            productRepository.save(newProduct);
            return productMapper.toProductResponse(newProduct);
            
        }
        throw new ProductAlreadyExistException("ce produit existe déjà");
    }

    @Override
    public ProductResponse updateProduct(String  productId, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            var productUpdate = product.get();
            productUpdate.setNomProduct(productRequest.getNomProduct());
            productUpdate.setPrice(productRequest.getPrice());
            productRepository.save(productUpdate);
            return productMapper.toProductResponse(productUpdate);
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

    // fonction de pagination et de tri
    @Override
    public PaginatedResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String direction) {
        // trier les produits par ordre croissant ou décroissant
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        // pagination des produits avec le nombre de produits par page et le numéro de la page
        Pageable pageable = PageRequest.of(page, size, sort);
        // récupérer les produits paginés
        Page<Product> products = productRepository.findAll(pageable);
        // mapper les produits paginés en produits de réponse
        List<ProductResponse> productResponses = products.getContent().stream().map(product ->
                    productMapper.toProductResponse(product)).collect(Collectors.toList());
        // retourner les produits paginés
        return new PaginatedResponse<>(productResponses, products.getNumber(), products.getSize(),
                products.getTotalElements(), products.getTotalPages());
        
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> productMapper.toProductResponse(product))
                                .collect(Collectors.toList());
    }
    

}
