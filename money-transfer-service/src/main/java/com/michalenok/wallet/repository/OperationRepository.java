package com.michalenok.wallet.repository;

import com.michalenok.wallet.model.entity.OperationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OperationRepository extends CrudRepository<OperationEntity, UUID>, PagingAndSortingRepository <OperationEntity, UUID>{
}