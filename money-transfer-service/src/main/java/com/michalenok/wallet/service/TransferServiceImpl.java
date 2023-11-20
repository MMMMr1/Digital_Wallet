package com.michalenok.wallet.service;

import com.michalenok.wallet.kafka.producer.api.MessageProducer;
import com.michalenok.wallet.kafka.schema.Transfer;
import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.mapper.TransferMapper;
import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.model.entity.TransferEntity;
import com.michalenok.wallet.model.enums.TransferStatus;
import com.michalenok.wallet.mapper.OperationMapper;
import com.michalenok.wallet.repository.TransferRepository;
import com.michalenok.wallet.service.api.TransferService;
import com.michalenok.wallet.service.util.TimeGenerationUtil;
import com.michalenok.wallet.service.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository operationRepository;
    private final MessageProducer<Transfer> messageProducer;
    private final OperationMapper operationMapper;
    private final TransferMapper transferMapper;
    private final TimeGenerationUtil timeGenerationUtil;
    private final UuidUtil uuidUtil;

    @Override
    @Transactional
    public void debitTransfer(TransferRequestDto debit) {
        TransferEntity operationEntity = initializeOperationEntity(debit, TransferType.DEBIT);
        operationRepository.save(operationEntity);
        messageProducer.sendMessage(transferMapper.toTransfer(operationEntity));
    }

    @Override
    @Transactional
    public void creditTransfer(TransferRequestDto credit) {
        TransferEntity operationEntity = initializeOperationEntity(credit, TransferType.CREDIT);
        operationRepository.save(operationEntity);
        messageProducer.sendMessage(transferMapper.toTransfer(operationEntity));
}

    @Override
    @Transactional
    public void internalFundTransfer(TransferRequestDto transfer){
        TransferEntity operationEntity = initializeOperationEntity(transfer, TransferType.INTERNAL);
        operationRepository.save(operationEntity);
        messageProducer.sendMessage(transferMapper.toTransfer(operationEntity));
    }

    @Override
    public Page<TransferInfoDto> getPage(Pageable pageable) {
        log.info("get page total elements {}",operationRepository.findAll(pageable).getTotalElements());
        return operationRepository.findAll(pageable)
                .map(operationMapper::toOperationInfoDto);
    }


    private TransferEntity initializeOperationEntity(TransferRequestDto dto, TransferType transferType){
        Instant time = timeGenerationUtil.generateCurrentInstant();
        TransferEntity operationEntity = operationMapper.toOperationEntity(dto);
        operationEntity.setUuid(uuidUtil.generateUuid());
        operationEntity.setType(transferType);
        operationEntity.setCreatedAt(time);
        operationEntity.setUpdatedAt(time);
        operationEntity.setStatus(TransferStatus.CREATED);
        return operationEntity;
    }
}