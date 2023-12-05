package com.michalenok.wallet.service;

import com.michalenok.wallet.mapper.TransferMapper;
import com.michalenok.wallet.model.dto.exception.TransferNotFoundException;
import com.michalenok.wallet.model.dto.request.TransferSpecificationDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.repository.TransferRepository;
import com.michalenok.wallet.service.api.TransferDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import static com.michalenok.wallet.service.util.TransferSpecification.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransferDetailsServiceImpl implements TransferDetailsService {
    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;

    @Override
    public Page<TransferInfoDto> getTransfers(Pageable pageable) {
        log.info("get page total elements {}", transferRepository.findAll(pageable).getTotalElements());
        return transferRepository.findAll(pageable)
                .map(transferMapper::transferEntityToTransferInfoDto);
    }

    public Page<TransferInfoDto> searchTransfers(TransferSpecificationDto specificationDto,
                                                 Pageable pageable) {
        return transferRepository.findAll(Specification
                        .where(hasTransferType(specificationDto.transferType()))
                        .and(hasAmountBetween(specificationDto.amountMin(), specificationDto.amountMax()))
                        .and(hasTimeBetween(specificationDto.timeAfter(), specificationDto.timeBefore())), pageable)
                .map(transferMapper::transferEntityToTransferInfoDto);
    }

    @Override
    public TransferInfoDto getTransfer(UUID uuid) {
        log.info("transfer of funds for transfer with uuid {}", uuid);
        return transferRepository.findById(uuid)
                .map(transferMapper::transferEntityToTransferInfoDto)
                .orElseThrow(() -> new TransferNotFoundException(String.format("transfer with uuid %s not found", uuid)));
    }

    @Override
    public List<TransferInfoDto> getTransfers(UUID accountUuid) {
        log.info("all transfer of funds for account with uuid {}", accountUuid);
        return transferRepository.findByAccountTo(accountUuid).stream()
                .map(transferMapper::transferEntityToTransferInfoDto)
                .toList();
    }

    @Override
    public Page<TransferInfoDto> searchTransfersByAccount(UUID accountUuid, TransferSpecificationDto specification, Pageable pageable) {
        return transferRepository.findAll(Specification
                        .where(hasAccountUuid(accountUuid))
                        .and(hasTransferType(specification.transferType()))
                        .and(hasAmountBetween(specification.amountMin(), specification.amountMax()))
                        .and(hasTimeBetween(specification.timeAfter(), specification.timeBefore())), pageable)
                .map(transferMapper::transferEntityToTransferInfoDto);
    }
}