package com.nourayni.facturation.productcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nourayni.facturation.dtos.ProductRequest;
import com.nourayni.facturation.dtos.ProductResponse;
import com.nourayni.facturation.exception.ProductAlreadyExistException;
import com.nourayni.facturation.exception.ProductNotFoundException;
import com.nourayni.facturation.productservice.ProductService;
import com.nourayni.facturation.utils.PaginatedResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    
        // Ajouter un produit
        @PostMapping
        public ResponseEntity<ProductResponse> saveProduct(@RequestBody ProductRequest productRequest) {
            try {
                ProductResponse productResponse = productService.saveProduct(productRequest);
                return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
            } catch (ProductAlreadyExistException ex) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }
    
        // Mettre à jour un produit
        @PostMapping("/{id}")
        public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
            try {
                ProductResponse productResponse = productService.updateProduct(id, productRequest);
                return ResponseEntity.ok(productResponse);
            } catch (ProductNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
    
        // Récupérer un produit par ID
        @GetMapping("/{id}")
        public ResponseEntity<ProductResponse> getProduct(@PathVariable String id) {
            try {
                ProductResponse productResponse = productService.getProduct(id);
                return ResponseEntity.ok(productResponse);
            } catch (ProductNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
    
        // Supprimer un produit
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
            try {
                productService.deleteProduct(id);
                return ResponseEntity.noContent().build();
            } catch (ProductNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
    
        // Récupérer tous les produits (avec pagination et tri)
        @GetMapping
        public ResponseEntity<PaginatedResponse<ProductResponse>> getAllProducts(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "nomProduct") String sortBy,
                @RequestParam(defaultValue = "asc") String direction
        ) {
            PaginatedResponse<ProductResponse> paginatedProducts = productService.getAllProducts(page, size, sortBy, direction);
            return ResponseEntity.ok(paginatedProducts);
        }

        @GetMapping("/")
        public ResponseEntity<List<ProductResponse>> getAllProducts(){
            try {
                List<ProductResponse> productResponse = productService.getAllProducts();
                return ResponseEntity.status(HttpStatus.OK).body(productResponse);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }

}
