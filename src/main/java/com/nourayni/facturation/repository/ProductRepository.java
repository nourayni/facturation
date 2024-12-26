package com.nourayni.facturation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nourayni.facturation.entity.Product;

public interface ProductRepository extends JpaRepository<Product,String> {
    Optional<Product> findByNomProduct(String nomProduct);
    Boolean existsByNomProduct(String nomProduct);

}
