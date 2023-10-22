package com.michalenok.wallet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID uuid;
    @Column(name = "mail")
    private String mail;
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column(name = "password")
    private String password;
    private String role;
    private String status;
    @Column(name = "dtcreate")
    private Instant dtCreate;
    @Version
    @Column(name = "dtupdate")
    private Instant dtUpdate;
    @ToString.Include(name = "password")
    private String maskPassword(){
        return "********";
    }
}
