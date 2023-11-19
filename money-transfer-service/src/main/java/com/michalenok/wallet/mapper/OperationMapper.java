package com.michalenok.wallet.mapper;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.OperationInfoDto;
import com.michalenok.wallet.model.entity.OperationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OperationMapper {
    OperationEntity toOperationEntity (TransferRequestDto transferRequestDto);
    OperationInfoDto toOperationInfoDto (OperationEntity operation);
}