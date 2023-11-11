package com.michalenok.wallet.repository;

import com.michalenok.wallet.model.entity.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, UUID>, PagingAndSortingRepository<AccountEntity, UUID> {
    List<AccountEntity> findByClientId(UUID clientId);
}
