package com.michalenok.wallet.service;

import com.michalenok.wallet.kafka.producer.api.MessageProducer;
import com.michalenok.wallet.kafka.schema.Transaction;
import com.michalenok.wallet.kafka.schema.Transfer;
import com.michalenok.wallet.kafka.schema.TransferType;
import com.michalenok.wallet.mapper.TransferMapper;
import com.michalenok.wallet.model.dto.exception.TransferNotFoundException;
import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import com.michalenok.wallet.model.entity.TransferEntity;
import com.michalenok.wallet.model.enums.TransferStatus;
import com.michalenok.wallet.repository.TransferRepository;
import com.michalenok.wallet.service.api.TransferService;
import com.michalenok.wallet.service.util.TimeGenerationUtil;
import com.michalenok.wallet.service.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import static com.michalenok.wallet.kafka.schema.TransactionStatus.SUCCESSFUL;
import static com.michalenok.wallet.model.enums.TransferStatus.COMPLETED;
import static com.michalenok.wallet.model.enums.TransferStatus.FAILED;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final MessageProducer<Transfer> messageProducer;
    private final TransferMapper transferMapper;
    private final TimeGenerationUtil timeGenerationUtil;
    private final UuidUtil uuidUtil;
    private final ConcurrentHashMap<UUID, CompletableFuture<Transaction>> transfers = new ConcurrentHashMap<>();

    @SneakyThrows
    @Override
    @Transactional
    public TransferInfoDto debitTransfer(TransferRequestDto debit) {
        TransferEntity transferEntity = initializeOperationEntity(debit, TransferType.DEBIT);
        transferRepository.save(transferEntity);
        messageProducer.sendMessage(transferMapper.toTransfer(transferEntity));

        CompletableFuture<Transaction> completableFuture = new CompletableFuture<>();
        transfers.put(transferEntity.getUuid(), completableFuture);
        return changeTransferStatus(completableFuture.get());
    }

    @SneakyThrows
    @Override
    @Transactional
    public TransferInfoDto creditTransfer(TransferRequestDto credit) {
        TransferEntity transferEntity = initializeOperationEntity(credit, TransferType.CREDIT);
        transferRepository.save(transferEntity);
        messageProducer.sendMessage(transferMapper.toTransfer(transferEntity));

        CompletableFuture<Transaction> completableFuture = new CompletableFuture<>();
        transfers.put(transferEntity.getUuid(), completableFuture);
        return changeTransferStatus(completableFuture.get());
    }

    @SneakyThrows
    @Override
    @Transactional
    public TransferInfoDto internalFundTransfer(TransferRequestDto transfer) {
        TransferEntity transferEntity = initializeOperationEntity(transfer, TransferType.INTERNAL);
        transferRepository.save(transferEntity);
        messageProducer.sendMessage(transferMapper.toTransfer(transferEntity));

        CompletableFuture<Transaction> completableFuture = new CompletableFuture<>();
        transfers.put(transferEntity.getUuid(), completableFuture);
        return changeTransferStatus(completableFuture.get());
    }

    @KafkaListener(topics = "${kafka.topic.transaction}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenMessage(Transaction transaction) {
        log.info("Transaction uuid {} ", transaction.getUuid());
        CompletableFuture<Transaction> transactionCompletableFuture = transfers.get(UUID.fromString(transaction.getUuid()));
        if (transactionCompletableFuture != null) {
            log.info("complete transaction {}", transactionCompletableFuture.toString());
            transactionCompletableFuture.complete(transaction);
        }
    }

    private TransferEntity initializeOperationEntity(TransferRequestDto transferRequestDto, TransferType transferType) {
        Instant time = timeGenerationUtil.generateCurrentInstant();
        TransferEntity transferEntity = transferMapper.transferRequestToTransferEntity(transferRequestDto);
        transferEntity.setUuid(uuidUtil.generateUuid());
        transferEntity.setType(transferType);
        transferEntity.setCreatedAt(time);
        transferEntity.setUpdatedAt(time);
        transferEntity.setStatus(TransferStatus.CREATED);
        return transferEntity;
    }

    private TransferInfoDto changeTransferStatus(Transaction transaction){
        log.info("change transfer status {} ", transaction.toString());
        UUID uuid = UUID.fromString(transaction.getUuid());
        return transferRepository.findById(uuid)
                .map(entity -> {
                            entity.setStatus(
                                    transaction.getStatus().equals(SUCCESSFUL.name()) ?
                                            COMPLETED : FAILED);
                            log.info("status {}", entity.getStatus());
                            return  entity;})
                .map(transferMapper::transferEntityToTransferInfoDto)
                .orElseThrow(() -> new TransferNotFoundException(String.format("transfer with uuid %s not found", uuid)));
    }
}