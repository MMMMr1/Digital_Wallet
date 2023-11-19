package com.michalenok.wallet.mapper;

import com.michalenok.wallet.kafka.schema.Transaction;
import com.michalenok.wallet.kafka.schema.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {
    Transaction toTransfer (Transfer transfer);
}