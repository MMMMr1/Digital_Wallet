package com.michalenok.wallet.repository;

import com.michalenok.wallet.model.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, UUID>, JpaSpecificationExecutor<TransferEntity> {
    List<TransferEntity> findByAccountTo(UUID accountTo);

    @Query("""
    SELECT transfer
    FROM TransferEntity transfer
    WHERE transfer.accountTo=:accountUuid AND transfer.type='DEBIT'
    """)
    List<TransferEntity> getDebitTransfers(@Param("accountUuid") UUID accountUuid);

    @Query("""
    SELECT transfer
    FROM TransferEntity transfer
    WHERE transfer.accountTo=:accountUuid AND transfer.type='CREDIT'
    """)
    List<TransferEntity> getCreditTransfers(@Param("accountUuid") UUID accountUuid);

    @Query("""
    SELECT transfer
    FROM TransferEntity transfer
    WHERE transfer.type='INTERNAL' 
          AND (transfer.accountTo=:accountUuid OR transfer.referenceNumber=:referenceNumber)
    """)
    List<TransferEntity> getInternalTransfers(@Param("accountUuid") UUID accountUuid,
                                              @Param("referenceNumber") String referenceNumber);
}