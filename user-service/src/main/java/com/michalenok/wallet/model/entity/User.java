package com.michalenok.wallet.model.entity;


import com.michalenok.wallet.model.constant.UserRole;
import com.michalenok.wallet.model.constant.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;


import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "app")
public class User {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID uuid;
    private String mail;
    private String mobilePhone;
    private String password;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(
            schema = "app",
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_uuid")
    )
    @Column(name = "role")
    private Set<UserRole> role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Instant dtCreate;
    @Version
    private Instant dtUpdate;
}
