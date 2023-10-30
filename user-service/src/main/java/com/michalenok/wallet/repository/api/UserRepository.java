package com.michalenok.wallet.repository.api;

import com.michalenok.wallet.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID>, PagingAndSortingRepository<UserEntity, UUID> {
    Optional<UserEntity> findByMail(String mail);
    Boolean existsByMail(String mail);
    Boolean existsByMobilePhone(String phoneNumber);
}
