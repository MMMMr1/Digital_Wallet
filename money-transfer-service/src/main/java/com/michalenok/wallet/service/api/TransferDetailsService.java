package com.michalenok.wallet.service.api;

import com.michalenok.wallet.model.dto.request.TransferSpecificationDto;
import com.michalenok.wallet.model.dto.response.TransferInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

/**
 * The {@code TransferDetailsService} interface defines the contract for the service layer to show details of funds transfer operations.
 */
public interface TransferDetailsService {
    /**
     * Returns a Page of  {@link TransferInfoDto} meeting the paging restriction provided in the Pageable object.
     * @param pageable must not be null.
     * @return never null.
     */
    Page<TransferInfoDto> getTransfers(Pageable pageable);

    /**
     * Returns a Page of  {@link TransferInfoDto} matching the given Specification.
     * @param specificationDto
     * @param pageable
     * @return
     */

    Page<TransferInfoDto> searchTransfers(TransferSpecificationDto specificationDto,
                                          Pageable pageable);

    /**
     * Returns a {@link TransferInfoDto} by the unique identifier of the funds transfer.
     * @param uuid the unique identifier of the funds transfer.
     * @return never null.
     */

    TransferInfoDto getTransfer(UUID uuid);

    /**
     * Returns list of {@link TransferInfoDto} by client's account unique identifier.
     * @param accountUuid the unique account identifier
     * @return never null.
     */

    List<TransferInfoDto> getTransfers(UUID accountUuid);

    /**
     * Returns a Page of  {@link TransferInfoDto} matching the given Specification.
     * @param accountUuid the unique account identifier
     * @param specification
     * @param pageable
     * @return never null.
     */

    Page<TransferInfoDto> searchTransfersByAccount(UUID accountUuid, TransferSpecificationDto specification, Pageable pageable);
}
