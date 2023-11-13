package com.michalenok.wallet.model.entity;

import com.michalenok.wallet.model.enums.TransferType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "transfers", schema = "app")
public class TransferEntity {
    @Id
    private UUID transactionUuid;
    @ManyToOne
    @JoinColumn(name = "account_number")
    private AccountEntity accountEntity;
    @Column(nullable = false, length = 3)
    private String currencyCode;
    @NotNull
    private BigDecimal amount;
    @Column(nullable = false, length = 25)
    private String referenceNumber;
    @Enumerated(EnumType.ORDINAL)
    private TransferType transactionType;
    @NotNull
    private Instant createdAt;
}