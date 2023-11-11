package com.michalenok.wallet.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "accounts", schema = "app")
public class AccountEntity {
    @Id
    private UUID accountNumber;
    private UUID clientId;
    @Column(nullable = false, length = 3)
    private String currencyCode;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal currentBalance;
    @Column(nullable = false)
    private LocalDate openDate;
    private LocalDate closeDate;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal maxLimit;
    @Column(nullable = false)
    private Boolean isActive;
}
