package com.smart.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smart.shop.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Client extends User{
    private String nom ;
    private String niveau_fidelete;
    public Client(int id , String username , String password , Role role , String nom , String niveau_fidelete){
        super(id , username , password , role);
        this.nom = nom;
        this.niveau_fidelete = niveau_fidelete;
    }

    @JsonIgnore // to avoid json infinite loop
    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;
}
