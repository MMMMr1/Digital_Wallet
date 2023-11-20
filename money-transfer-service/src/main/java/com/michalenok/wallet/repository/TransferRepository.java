package com.michalenok.wallet.repository;

import com.michalenok.wallet.model.entity.TransferEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TransferRepository extends CrudRepository<TransferEntity, UUID>, PagingAndSortingRepository <TransferEntity, UUID>{
}