package com.michalenok.wallet.repository.api;

import com.michalenok.wallet.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID>, PagingAndSortingRepository<User, UUID> {
    Optional<User> findByMail(String mail);
    Boolean existsByMail(String mail);
    Boolean existsByMobilePhone(String phoneNumber);


}
