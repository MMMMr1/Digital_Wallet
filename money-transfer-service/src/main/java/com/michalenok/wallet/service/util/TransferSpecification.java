package com.michalenok.wallet.service.util;

import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.model.dto.request.TransferSpecificationDto;
import com.michalenok.wallet.model.entity.TransferEntity;
import com.michalenok.wallet.model.enums.TransferStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
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
    public static Specification<TransferEntity> search(TransferSpecificationDto specification) {
        TransferType transferType = specification.transferType();
        TransferStatus transferStatus = specification.transferStatus();
        Instant timeAfter = specification.timeAfter();
        Instant timeBefore = specification.timeBefore();
        BigDecimal amountMax = specification.amountMax();
        BigDecimal amountMin = specification.amountMin();

        return (root, query, criteriaBuilder) -> {
            CriteriaBuilder cb = criteriaBuilder;
            Predicate finalPredicate = cb.conjunction();
            if (Objects.nonNull(transferType)) {
                finalPredicate = cb.and(
                        finalPredicate,
                        cb.and(
                                cb.equal(root.get("type"), transferType)
                        )
                );
            }
            if (Objects.nonNull(transferStatus)) {
                finalPredicate = cb.and(
                        finalPredicate,
                        cb.and(
                                cb.equal(root.get("status"), transferStatus)
                        )
                );
            }
            if (timeAfter != null && timeBefore != null) {
                finalPredicate = cb.and(
                        finalPredicate,
                        cb.and(
                                cb.between(root.get("createdAt"), timeAfter, timeBefore)
                        )
                );
            }
            if (amountMax != null && amountMin != null) {
                finalPredicate = cb.and(
                        finalPredicate,
                        cb.and(
                                cb.between(root.get("amount"), amountMin, amountMax)
                        )
                );
            }
            return finalPredicate;
        };
    }
    public static Specification<TransferEntity> search(TransferSpecificationDto specification, UUID accountUuid) {
        Specification<TransferEntity> search = search(specification);
        return search.and((root, query, criteriaBuilder) -> {
            CriteriaBuilder cb = criteriaBuilder;
            Predicate finalPredicate = cb.conjunction();
            if (Objects.nonNull(accountUuid)) {
                finalPredicate = cb.and(
                        finalPredicate,
                        cb.and(
                                cb.equal(root.get("accountTo"), accountUuid)
                        )
                );
            }

            return finalPredicate;
        });
    }
}
