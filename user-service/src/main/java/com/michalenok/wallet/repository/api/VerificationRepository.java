package com.michalenok.wallet.repository.api;

import com.michalenok.wallet.model.entity.VerificationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends CrudRepository<VerificationEntity, String> {
    void deleteByMail(String mail);
}
