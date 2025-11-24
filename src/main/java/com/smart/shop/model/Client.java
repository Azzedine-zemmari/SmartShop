package com.smart.shop.model;

import com.smart.shop.enums.Role;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
