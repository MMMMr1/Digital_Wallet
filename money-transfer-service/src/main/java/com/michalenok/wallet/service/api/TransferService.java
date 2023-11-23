package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.TransferRequestDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;

/**
 * The {@code TransferService} interface defines the contract for the service layer to manage funds transfer operations.
 */
public interface TransferService {
    /**
     * This method allows to replenish the client's account
     * @param debit The {@link TransferRequestDto} containing transfer details
     * @return {@link TransferInfoDto} representing transfer result
     */
    TransferInfoDto debitTransfer(TransferRequestDto debit);

    /**
     * This method allows to transfer money from the client's account
     * @param credit The {@link TransferRequestDto} containing transfer details
     * @return {@link TransferInfoDto} representing transfer result
     */
    TransferInfoDto creditTransfer(TransferRequestDto credit);

    /**
     * This method allows to transfer money from the client's account to another account
     * @param transfer The {@link TransferRequestDto} containing transfer details
     * @return {@link TransferInfoDto} representing transfer result
     */
    TransferInfoDto internalFundTransfer(TransferRequestDto transfer);
}