package com.michalenok.wallet.service;

import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.mapper.TransferMapper;
import com.michalenok.wallet.model.dto.exception.TransferNotFoundException;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.model.entity.TransferEntity;
import com.michalenok.wallet.model.enums.TransferStatus;
import com.michalenok.wallet.repository.TransferRepository;
import com.michalenok.wallet.service.api.TransferDetailsService;
import com.michalenok.wallet.service.util.TransferSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
    public Page<TransferInfoDto> searchTransfers(TransferType transferType,
                                                 TransferStatus transferStatus,
                                                 Instant timeAfter,
                                                 Instant timeBefore,
                                                 Pageable pageable){
        Specification<TransferEntity> search = TransferSpecification.search(transferType, transferStatus, timeAfter, timeBefore);
        return transferRepository.findAll(search, pageable)
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
    public List<TransferInfoDto> getDebitTransfers(UUID accountUuid) {
        log.info("all transfers of debit funds for account with uuid {}", accountUuid);
        return transferRepository.getDebitTransfers(accountUuid).stream()
                .map(transferMapper::transferEntityToTransferInfoDto)
                .toList();
    }

    @Override
    public List<TransferInfoDto> getCreditTransfers(UUID accountUuid) {
        log.info("all transfers of credit funds for account with uuid {}", accountUuid);
        return transferRepository.getCreditTransfers(accountUuid).stream()
                .map(transferMapper::transferEntityToTransferInfoDto)
                .toList();
    }

    @Override
    public List<TransferInfoDto> getInternalTransfers(UUID accountUuid) {
        log.info("all transfers of internal funds for account with uuid {}", accountUuid);
        return transferRepository.getInternalTransfers(accountUuid, accountUuid.toString()).stream()
                .map(transferMapper::transferEntityToTransferInfoDto)
                .toList();
    }

    @Override
    public List<TransferInfoDto> getTransfersByPeriod(UUID accountUuid, Instant timeAfter, Instant timeBefore) {
        log.info("all transfers of funds for account with uuid {} for period from {} to {}", accountUuid, timeAfter, timeBefore);
        return transferRepository.findByPeriod(accountUuid,
                        accountUuid.toString(),
                        timeAfter,
                        timeBefore).stream()
                .map(transferMapper::transferEntityToTransferInfoDto)
                .toList();
    }
}