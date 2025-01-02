package com.nourayni.facturation.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter @Builder
public class FactureDayResponse {
    private List<FacturationResponseDTO> facturationResponseDTOs = new ArrayList<FacturationResponseDTO>();
    private Double totalAmountDay;

}
