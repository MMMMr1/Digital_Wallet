package com.michalenok.wallet.repository.api;

import com.michalenok.wallet.model.entity.Verification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends CrudRepository<Verification, String> {
}
