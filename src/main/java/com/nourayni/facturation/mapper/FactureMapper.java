package com.nourayni.facturation.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nourayni.facturation.dtos.FacturationDTO;
import com.nourayni.facturation.dtos.FacturationResponseDTO;
import com.nourayni.facturation.dtos.LigneFacturationDTO;
import com.nourayni.facturation.entity.Facturation;
import com.nourayni.facturation.entity.LigneFacturation;

@Service
public class FactureMapper {

    public LigneFacturationDTO toLigneFacturationDTO(LigneFacturation ligne) {
        LigneFacturationDTO dto = LigneFacturationDTO.builder()
        .product(ligne.getProduct())
        .quantity(ligne.getQuantity())
        .price(ligne.getPrice())
        .build();
        return dto;
    }

    public FacturationResponseDTO toFacturationResponseDTO(Facturation facturation){
        return new FacturationResponseDTO(
            facturation.getId(),
            facturation.getNumFacture(),
            facturation.getClientName(),
            facturation.getClientPhoneNumber(),
            facturation.getLignesFacturation()
                    .stream().map(this::toLigneFacturationDTO)
                            .collect(Collectors.toList()),
            facturation.getTotalAmount(),
            facturation.getCreatedAt(),
            facturation.getUpdatedAt()
        );
    }

    public LigneFacturation toLigneFacturation(LigneFacturationDTO ligneFacturationDTO){
        return LigneFacturation.builder()
        .product(ligneFacturationDTO.getProduct())
        .quantity(ligneFacturationDTO.getQuantity())
        .price(ligneFacturationDTO.getPrice())
        .build();
    }

    public Facturation toFacturation(FacturationDTO facturationResponseDTO){
        return Facturation.builder()
        .clientName(facturationResponseDTO.getClientName())
        .clientPhoneNumber(facturationResponseDTO.getClientPhoneNumber())
        .lignesFacturation(facturationResponseDTO.getLignesFacturation()
                .stream().map(this::toLigneFacturation)
                .collect(Collectors.toList()))
        .build();
    }
}
