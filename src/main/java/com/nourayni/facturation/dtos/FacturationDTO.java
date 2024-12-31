package com.nourayni.facturation.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FacturationDTO {
    private String clientName;
    private String clientPhoneNumber;
    private List<LigneFacturationDTO> lignesFacturation = new ArrayList<>();

}
