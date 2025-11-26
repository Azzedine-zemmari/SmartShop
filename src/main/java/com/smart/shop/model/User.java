package com.smart.shop.model;


import com.smart.shop.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password ;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Client client;

   @Override
    public int hashCode(){
       return id == null ? 0 : id.hashCode();
   }

   @Override
    public boolean equals(Object obj){
       if(this == obj) return true;
       if(!(obj instanceof  User)) return false;

       User another = (User) obj;

       return this.id != null && this.id.equals(another.id);

   }

}
