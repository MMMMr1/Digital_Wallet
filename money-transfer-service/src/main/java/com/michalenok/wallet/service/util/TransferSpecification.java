package com.michalenok.wallet.service.util;

import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.model.entity.TransferEntity;
import com.michalenok.wallet.model.enums.TransferStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Component
public class TransferSpecification {
    public static Specification<TransferEntity> hasTransferType(TransferType transferType) {
        if (Objects.isNull(transferType)) return null;
        return (transferEntity, cq, cb) ->( cb.equal(transferEntity.get("type"), transferType));
    }

    public static Specification<TransferEntity> hasTransferType(TransferStatus transferStatus) {
        if (Objects.isNull(transferStatus)) return null;
        return (transferEntity, cq, cb) ->( cb.equal(transferEntity.get("status"), transferStatus));
    }

    public static Specification<TransferEntity> hasAccountUuid(UUID accountUuid) {
        if (Objects.isNull(accountUuid)) return null;
        return (transferEntity, cq, cb) ->( cb.equal(transferEntity.get("accountTo"), accountUuid));
    }

    public static Specification<TransferEntity> hasTimeBetween(Instant timeAfter, Instant timeBefore) {
        if (Objects.isNull(timeAfter) || Objects.isNull(timeBefore)) return null;
        return (transferEntity, cq, cb) ->( cb.between(transferEntity.get("createdAt"), timeAfter, timeBefore));
    }

    public static Specification<TransferEntity> hasAmountBetween(BigDecimal amountMax, BigDecimal amountMin) {
        if (Objects.isNull(amountMax) || Objects.isNull(amountMin)) return null;
        return (transferEntity, cq, cb) ->( cb.between(transferEntity.get("amount"), amountMin, amountMax));
    }
}
