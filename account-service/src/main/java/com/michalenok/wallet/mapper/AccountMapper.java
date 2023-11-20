package com.michalenok.wallet.mapper;

import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.model.entity.AccountEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    AccountInfoDto toAccountInfo(AccountEntity account);

    @InheritInverseConfiguration
    AccountEntity toAccountEntity(AccountInfoDto account);
}
