package com.nourayni.facturation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nourayni.facturation.entity.Facturation;

public interface FacturationRepository extends JpaRepository<Facturation, String> {

    List<Facturation> findByCreatedAt(LocalDateTime date);
    Page<Facturation> findByNumFactureContaining(String numFacture,Pageable pageable);

}
