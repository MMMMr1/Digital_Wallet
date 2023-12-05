package com.michalenok.wallet.kafka.command;

import com.michalenok.wallet.kafka.schema.TransferType;
import lombok.AllArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ExecutorTransferCommand {
    Map<TransferType, TransferCommand> commands = new HashMap<>();

    public ExecutorTransferCommand() {
        commands.put(TransferType.CREDIT, new CreditCommand());
        commands.put(TransferType.DEBIT, new DebitCommand());
        commands.put(TransferType.INTERNAL, new InternalCommand());
    }

    public TransferCommand transfer(TransferType transferType) {
        return commands.get(transferType);
    }
}