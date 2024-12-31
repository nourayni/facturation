package com.nourayni.facturation.factureservice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nourayni.facturation.dtos.FacturationDTO;
import com.nourayni.facturation.dtos.FacturationResponseDTO;
import com.nourayni.facturation.dtos.LigneFacturationDTO;
import com.nourayni.facturation.dtos.ProductResponse;
import com.nourayni.facturation.entity.Facturation;
import com.nourayni.facturation.entity.LigneFacturation;
import com.nourayni.facturation.entity.Product;
import com.nourayni.facturation.mapper.FactureMapper;
import com.nourayni.facturation.mapper.ProductMapper;
import com.nourayni.facturation.productservice.ProductService;
import com.nourayni.facturation.repository.FacturationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FactureService {
    private final FactureMapper factureMapper;
    private final FacturationRepository facturationRepository;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public void calculateLignePrice(LigneFacturationDTO ligneFacturation){
        if(ligneFacturation.getProduct() == null){
            throw new IllegalArgumentException("produit non trouve");
        }
        ProductResponse product = productService.getProduct(ligneFacturation.getProduct().getId());
        ligneFacturation.setProduct(productMapper.toProduct(product));
        ligneFacturation.setPrice(ligneFacturation.getProduct().getPrice() * ligneFacturation.getQuantity());
    }

    /**
     * Calcule le montant total d'une facturation.
     */
    public double calculateTotalAmount(List<LigneFacturation> lignesFacturation){
        return lignesFacturation.stream().mapToDouble(LigneFacturation :: getPrice).sum();
    }

    public Facturation saveFacturation(FacturationDTO facturation){
        //facturation.getLignesFacturation().forEach(this::calculateLignePrice);
        facturation.getLignesFacturation().forEach(ligne -> calculateLignePrice(ligne));
        Facturation newFacturation = factureMapper.toFacturation(facturation);
        newFacturation.setTotalAmount(calculateTotalAmount(newFacturation.getLignesFacturation()));
        return facturationRepository.save(newFacturation);
    }

    public List<Facturation> listFactureToDay(LocalDateTime date){
        List<Facturation> factures = facturationRepository.findAll();
        
        List<Facturation>  factureOnDay = factures.stream().filter(facture -> facture.
                getCreatedAt().toLocalDate().equals(date.toLocalDate())).collect(Collectors.toList());

                //Double totalSum = factureOnDay.stream().mapToDouble(Facturation::getTotalAmount).sum();
        return factureOnDay;

    }

    public List<FacturationResponseDTO> getAllFactures(){
        List<Facturation> factures = facturationRepository.findAll();
        return factures.stream().map(factureMapper::toFacturationResponseDTO).collect(Collectors.toList());
    }



}
