package com.smart.shop.model;


import com.smart.shop.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    protected String username;

    protected String password ;

    @Enumerated(EnumType.STRING)
    protected Role role;

    @OneToOne(mappedBy = "user")
    private Client client;
}
