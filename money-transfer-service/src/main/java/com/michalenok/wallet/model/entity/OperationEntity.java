package com.michalenok.wallet.model.entity;

import com.michalenok.wallet.model.enums.OperationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders", schema = "app")
@Getter @Setter
public class OperationEntity {
    @Id
    private UUID uuid;
    private UUID accountTo;
    private String referenceNumber;
    private BigDecimal amount;
    private String currencyCode;
    @Enumerated(EnumType.STRING)
    private OperationStatus status;
    private Instant createdAt;
    @Version
    private Instant updatedAt;
}