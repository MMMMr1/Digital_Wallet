package com.michalenok.wallet.service;

import com.michalenok.wallet.kafka.producer.api.MessageProducer;
import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.entity.OperationEntity;
import com.michalenok.wallet.model.enums.OperationStatus;
import com.michalenok.wallet.mapper.OperationMapper;
import com.michalenok.wallet.repository.OperationRepository;
import com.michalenok.wallet.service.api.TransferService;
import com.michalenok.wallet.service.util.TimeGenerationUtil;
import com.michalenok.wallet.service.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final OperationRepository operationRepository;
    private final MessageProducer<TransferRequestDto> messageProducer;
    private final OperationMapper operationMapper;
    @Value("${avro.topic.name.debit_operation}")
    private String debitOperationTopicName;
    @Value("${avro.topic.name.credit_operation}")
    private String creditOperationTopicName;
    @Value("${avro.topic.name.internal_operation}")
    private String internalOperationTopicName;
    private final TimeGenerationUtil timeGenerationUtil;
    private final UuidUtil uuidUtil;

    @Override
    @Transactional
    public void debitTransfer(TransferRequestDto debit) {
        OperationEntity operationEntity = initializeOperationEntity(debit);
        operationRepository.save(operationEntity);
        messageProducer.sendMessage(debit, operationEntity.getUuid(), debitOperationTopicName);
    }

    @Override
    @Transactional
    public void creditTransfer(TransferRequestDto credit) {
        OperationEntity operationEntity = initializeOperationEntity(credit);
        operationRepository.save(operationEntity);
        messageProducer.sendMessage(credit, operationEntity.getUuid(), creditOperationTopicName);
    }

    @Override
    @Transactional
    public void internalFundTransfer(TransferRequestDto transfer){
        OperationEntity operationEntity = initializeOperationEntity(transfer);
        operationRepository.save(operationEntity);
        messageProducer.sendMessage(transfer, operationEntity.getUuid(), internalOperationTopicName);
    }

    private OperationEntity initializeOperationEntity(TransferRequestDto dto){
        Instant time = timeGenerationUtil.generateCurrentInstant();
        OperationEntity operationEntity = operationMapper.toOperationEntity(dto);
        operationEntity.setUuid(uuidUtil.generateUuid());
        operationEntity.setCreatedAt(time);
        operationEntity.setUpdatedAt(time);
        operationEntity.setStatus(OperationStatus.CREATED);
        return operationEntity;
    }
}