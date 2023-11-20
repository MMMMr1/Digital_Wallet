package com.michalenok.wallet.mapper;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.model.entity.TransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OperationMapper {
    TransferEntity toOperationEntity (TransferRequestDto transferRequestDto);
    TransferInfoDto toOperationInfoDto (TransferEntity operation);
}