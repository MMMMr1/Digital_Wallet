package com.michalenok.wallet.model.entity;

import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
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
    private String mail;
    private String mobilePhone;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Instant dtCreate;
    @Version
    private Instant dtUpdate;
    @ToString.Include(name = "password")
    private String maskPassword(){
        return "********";
    }
}
