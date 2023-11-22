package com.michalenok.wallet.service.util;

import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.model.entity.TransferEntity;
import com.michalenok.wallet.model.enums.TransferStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Objects;

@Component
public class TransferSpecification {
    public static Specification<TransferEntity> search(TransferType transferType,
                                                       TransferStatus transferStatus,
                                                       Instant timeAfter,
                                                       Instant timeBefore) {
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
            return finalPredicate;
        };
    }
}
