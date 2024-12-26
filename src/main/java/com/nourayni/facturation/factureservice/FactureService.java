package com.nourayni.facturation.factureservice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nourayni.facturation.entity.Facturation;
import com.nourayni.facturation.entity.LigneFacturation;
import com.nourayni.facturation.repository.FacturationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FactureService {
    private final FacturationRepository facturationRepository;

    public void calculateLignePrice(LigneFacturation ligneFacturation){
        if(ligneFacturation.getProduct() == null){
            throw new IllegalArgumentException("produit non trouve");
        }
        ligneFacturation.setPrice(ligneFacturation.getProduct().getPrice() * ligneFacturation.getQuantity());
    }

    /**
     * Calcule le montant total d'une facturation.
     */
    public double calculateTotalAmount(List<LigneFacturation> lignesFacturation){
        return lignesFacturation.stream().mapToDouble(LigneFacturation :: getPrice).sum();
    }

    public Facturation saveFacturation(Facturation facturation){
        //facturation.getLignesFacturation().forEach(this::calculateLignePrice);
        facturation.getLignesFacturation().forEach(ligne -> calculateLignePrice(ligne));

        facturation.setTotalAmount(calculateTotalAmount(facturation.getLignesFacturation()));
        return facturationRepository.save(facturation);
    }

    public List<Facturation> listFactureToDay(LocalDateTime date){
        List<Facturation> factures = facturationRepository.findAll();
        
        List<Facturation>  factureOnDay = factures.stream().filter(facture -> facture.
                getCreatedAt().toLocalDate().equals(date.toLocalDate())).collect(Collectors.toList());

                //Double totalSum = factureOnDay.stream().mapToDouble(Facturation::getTotalAmount).sum();
        return factureOnDay;

    }





}
