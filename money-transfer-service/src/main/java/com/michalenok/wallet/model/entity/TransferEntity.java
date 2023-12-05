package com.michalenok.wallet.model.entity;

import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.model.enums.TransferStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter @Setter
@Builder
@Entity
@Table(name = "transfers", schema = "app")
@NoArgsConstructor
@AllArgsConstructor
public class TransferEntity {
    @Id
    private UUID id;
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