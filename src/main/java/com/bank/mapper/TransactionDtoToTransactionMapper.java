package com.bank.mapper;

import com.bank.data.entity.Transaction;
import com.bank.dto.TransactionDTO;

import javax.validation.constraints.NotNull;

public class TransactionDtoToTransactionMapper implements Mapper<TransactionDTO, Transaction> {

    @Override
    public Transaction map(@NotNull TransactionDTO from) {
        return new Transaction(
                from.getId(),
                from.getType(),
                from.getDate(),
                from.getValue(),
                from.getOriginAccount(),
                from.getOriginAccountBank(),
                from.getDestinationAccount(),
                from.getDestinationAccountBank(),
                from.getDescription()
        );
    }
}
