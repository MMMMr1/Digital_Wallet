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
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "app")
public class UserEntity {
    @Id
    @Column(name = "uuid", nullable = false)
    private UUID uuid;
    @NotNull
    private String mail;
    @NotNull
    private String mobilePhone;
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    @CollectionTable(
            schema = "app",
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_uuid")
    )
    @Column(name = "role")
    private Set<UserRole> role;
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;
    @NotNull
    private Instant createdAt;
    @Version
    @NotNull
    private Instant updatedAt;
}
