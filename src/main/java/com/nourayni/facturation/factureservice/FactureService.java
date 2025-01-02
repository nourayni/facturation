package com.nourayni.facturation.factureservice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nourayni.facturation.dtos.FacturationDTO;
import com.nourayni.facturation.dtos.FacturationResponseDTO;
import com.nourayni.facturation.dtos.FactureDayResponse;
import com.nourayni.facturation.dtos.LigneFacturationDTO;
import com.nourayni.facturation.dtos.ProductResponse;
import com.nourayni.facturation.entity.Facturation;
import com.nourayni.facturation.entity.LigneFacturation;
import com.nourayni.facturation.entity.Product;
import com.nourayni.facturation.mapper.FactureMapper;
import com.nourayni.facturation.mapper.ProductMapper;
import com.nourayni.facturation.productservice.ProductService;
import com.nourayni.facturation.repository.FacturationRepository;
import com.nourayni.facturation.utils.GenerateNumFacture;
import com.nourayni.facturation.utils.PaginatedResponse;

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
        newFacturation.setNumFacture("drame"+GenerateNumFacture.generateInvoiceNumber());
        newFacturation.setTotalAmount(calculateTotalAmount(newFacturation.getLignesFacturation()));
        return facturationRepository.save(newFacturation);
    }

    public FactureDayResponse listFactureToDay(LocalDate date){
        List<Facturation> factures = facturationRepository.findAll();
        
        List<Facturation>  factureOnDay = factures.stream().filter(facture -> facture.
                getCreatedAt().toLocalDate().equals(date)).collect(Collectors.toList());

                Double totalSum = factureOnDay.stream().mapToDouble(Facturation::getTotalAmount).sum();
                List<FacturationResponseDTO> facturation =  factureOnDay.stream().map(fact -> factureMapper.toFacturationResponseDTO(fact))
                            .collect(Collectors.toList());
        return FactureDayResponse.builder()
        .facturationResponseDTOs(facturation)
        .totalAmountDay(totalSum)
        .build();

    }

    public List<FacturationResponseDTO> getAllFactures(){
        List<Facturation> factures = facturationRepository.findAll();
        return factures.stream().map(factureMapper::toFacturationResponseDTO).collect(Collectors.toList());
    }

    public PaginatedResponse<FacturationResponseDTO> getPaginatedResponseFacture(int page,
    int size,
        String sortBy,
            String direction,
                String numFacture){
        Sort sort = direction.equals("desc")
            ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Facturation> factures;

        if(numFacture != null && !numFacture.isEmpty()){
            //rechercher par numero de facture
            factures = facturationRepository.findByNumFactureContaining(numFacture, pageable);
        }else{
            //rechercher tous les factures
            factures = facturationRepository.findAll(pageable);
        }
        List<FacturationResponseDTO> facturationResponseDTOs = factures.getContent().stream()
                        .map(factureMapper::toFacturationResponseDTO).collect(Collectors.toList());

        return new PaginatedResponse<>(facturationResponseDTOs,
        factures.getNumber(),
        factures.getSize(),
        factures.getTotalElements(),
        factures.getTotalPages());
    }

    public FacturationResponseDTO getFacture(String id){
        Facturation facture = facturationRepository.findById(id).orElseThrow(() ->
                                        new IllegalArgumentException("Facture non trouve"));
        return factureMapper.toFacturationResponseDTO(facture);
    }


}
