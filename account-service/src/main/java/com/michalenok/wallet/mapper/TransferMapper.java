package com.michalenok.wallet.mapper;

import com.michalenok.wallet.kafka.schema.Transfer;
import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransferMapper {
    @Mapping(target = "accountTo", expression = "java(UUID.fromString(transfer.getAccountTo()))")
    TransferRequestDto toTransfer (Transfer transfer);
}