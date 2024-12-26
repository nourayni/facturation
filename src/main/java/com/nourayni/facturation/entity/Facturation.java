package com.nourayni.facturation.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Facturation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String clientName;
    private String clientPhoneNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneFacturation> lignesFacturation = new ArrayList<>();

    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void setCreatedAt(){
        createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void setUpdatedAt(){
        updatedAt = LocalDateTime.now();
    }
}
