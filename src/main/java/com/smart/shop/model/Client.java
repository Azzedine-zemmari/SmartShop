package com.smart.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom ;
    @Enumerated(EnumType.STRING)
    private Niveau_fidelete niveau_fidelete ;
    private String email;

    @JsonIgnore // to avoid json infinite loop
    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;

    @OneToOne(optional = false)
    @JoinColumn(name="user_id",referencedColumnName = "id",nullable = false)
    private User user;

    @Override
    public int hashCode(){
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof Client)) return false;

        Client other = (Client) obj;
        return this.id != null && this.id.equals(other.id);
    }
}
