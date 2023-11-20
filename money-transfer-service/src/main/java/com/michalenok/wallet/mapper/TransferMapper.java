package com.michalenok.wallet.mapper;

import com.michalenok.wallet.kafka.schema.Transfer;
import com.michalenok.wallet.model.entity.TransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransferMapper {
    @Mapping(target = "accountTo", expression = "java(operation.getAccountTo().toString())")
    @Mapping(target = "uuid", expression = "java(operation.getUuid().toString())")
    Transfer toTransfer (TransferEntity operation);

}