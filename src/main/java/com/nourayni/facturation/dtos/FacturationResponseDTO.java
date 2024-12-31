package com.nourayni.facturation.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FacturationResponseDTO {
    private String id;
    private String clientName;
    private String clientPhoneNumber;
    private List<LigneFacturationDTO> lignesFacturation;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
