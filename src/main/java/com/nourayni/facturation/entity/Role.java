package com.nourayni.facturation.entity;

import com.nourayni.facturation.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private RoleName roleName;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    
}
