package com.michalenok.wallet.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "accounts", schema = "app")
public class AccountEntity {
    @Id
    private UUID accountNumber;
    @NotNull
    private UUID clientId;
    @Column(nullable = false, length = 3)
    private String currencyCode;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal currentBalance;
    @Column(nullable = false)
    private Instant openDate;
    private Instant closeDate;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal maxLimit;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal blockedSum;
    @Column(nullable = false)
    private Boolean isActive;
    @Version
    private Instant updatedAt;
}
