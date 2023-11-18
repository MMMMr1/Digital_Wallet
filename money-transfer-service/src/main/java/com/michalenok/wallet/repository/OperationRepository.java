package com.michalenok.wallet.repository;

import com.michalenok.wallet.model.entity.OperationEntity;
import org.apache.kafka.common.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, Uuid> {
}