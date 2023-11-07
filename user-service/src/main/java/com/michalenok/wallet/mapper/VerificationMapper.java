package com.michalenok.wallet.mapper;

import com.michalenok.wallet.kafka.schema.Verification;
import com.michalenok.wallet.model.entity.VerificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VerificationMapper {

    Verification toVerification(VerificationEntity user);
}