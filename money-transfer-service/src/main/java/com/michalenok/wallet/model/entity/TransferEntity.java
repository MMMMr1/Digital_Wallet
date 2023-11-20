package com.michalenok.wallet.model.entity;

import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.model.enums.TransferStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transfers", schema = "app")
@Getter @Setter
public class TransferEntity {
    @Id
    private UUID uuid;
    private UUID accountTo;
    private String referenceNumber;
    private BigDecimal amount;
    private String currencyCode;
    @Enumerated(EnumType.STRING)
    private TransferStatus status;
    @Enumerated(EnumType.STRING)
    private TransferType type;
    private Instant createdAt;
    @Version
    private Instant updatedAt;
}