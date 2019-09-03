package com.bank.mapper;

import com.bank.data.entity.Transaction;
import com.bank.dto.TransactionDTO;

import javax.validation.constraints.NotNull;

public class TransactionToTransactionDtoMapper implements Mapper<Transaction, TransactionDTO> {
    @Override
    public TransactionDTO map(@NotNull Transaction from) {
        return new TransactionDTO(
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
