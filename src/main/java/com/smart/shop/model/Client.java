package com.smart.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom ;
    @Enumerated(EnumType.STRING)
    private Niveau_fidelete niveau_fidelete;
    private String email;

    @JsonIgnore // to avoid json infinite loop
    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;

    @OneToOne(optional = false)
    @JoinColumn(name="user_id",referencedColumnName = "id",nullable = false)
    private User user;
}
