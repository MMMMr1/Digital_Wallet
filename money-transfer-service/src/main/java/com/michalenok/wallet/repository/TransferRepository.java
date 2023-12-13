package com.michalenok.wallet.repository;

import com.michalenok.wallet.model.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, UUID>, JpaSpecificationExecutor<TransferEntity> {

    List<TransferEntity> findByAccountTo(UUID accountTo);
}