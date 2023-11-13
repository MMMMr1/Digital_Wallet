package com.michalenok.wallet.repository;

import com.michalenok.wallet.model.entity.TransferEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends CrudRepository<TransferEntity, Long> {
}