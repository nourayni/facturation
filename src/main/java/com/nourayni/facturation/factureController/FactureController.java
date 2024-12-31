package com.nourayni.facturation.factureController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nourayni.facturation.dtos.FacturationDTO;
import com.nourayni.facturation.dtos.FacturationResponseDTO;
import com.nourayni.facturation.entity.Facturation;
import com.nourayni.facturation.factureservice.FactureService;
import com.nourayni.facturation.mapper.FactureMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facture")
public class FactureController {

    private final FactureService factureService;
    private final FactureMapper factureMapper;

    
    @PostMapping("/save")
    public ResponseEntity<FacturationResponseDTO> newFacture(@RequestBody FacturationDTO facturationDTO){
        try {
            Facturation facture = factureService.saveFacturation(facturationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(factureMapper.toFacturationResponseDTO(facture));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<FacturationResponseDTO>> listAllFactures(){
        List<FacturationResponseDTO> factures = factureService.getAllFactures();
        return ResponseEntity.status(HttpStatus.OK).body(factures);
    }


}
