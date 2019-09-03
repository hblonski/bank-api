package com.bank.service;

import com.bank.data.entity.Account;
import com.bank.data.entity.Transaction;
import com.bank.data.repository.AccountRepository;
import com.bank.data.repository.TransactionRepository;
import com.bank.data.value.BankProperties;
import com.bank.data.value.TransactionType;
import com.bank.dto.TransactionDTO;
import com.bank.exception.TransactionException;
import com.bank.mapper.TransactionDtoToTransactionMapper;
import com.bank.mapper.TransactionToTransactionDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;
import java.time.Instant;

public class TransactionServiceImpl implements TransactionService {

    @Inject
    private TransactionRepository transactionRepository;

    @Inject
    private AccountRepository accountRepository;

    @Override
    public TransactionDTO transfer(@NotNull TransactionDTO transactionDTO) throws TransactionException,
                                                                           EntityNotFoundException {
        if (transactionDTO.getType() != TransactionType.TRANSFER) {
            throw new InvalidParameterException("Wrong transaction type.");
        }
        Integer originBank = transactionDTO.getOriginAccountBank();
        Integer destinationBank = transactionDTO.getDestinationAccountBank();

        if (!BankProperties.BANK_CODE.equals(originBank) && !BankProperties.BANK_CODE.equals(destinationBank)) {
            throw new TransactionException("Accounts do not belong to this bank.");
        }

        String originAccountNumber = transactionDTO.getOriginAccount();
        String destinationAccountNumber = transactionDTO.getDestinationAccount();
        Account origin = accountRepository.findByNumberAndBank(originAccountNumber, originBank);
        Account destination = accountRepository.findByNumberAndBank(destinationAccountNumber, destinationBank);

        if (origin == null && destination == null) {
            throw new EntityNotFoundException("Accounts not found.");
        }

        if (origin == null) {
            if (!BankProperties.BANK_CODE.equals(originBank)) {
                origin = accountRepository.save(new Account(null, originAccountNumber, null, originBank));
            } else {
                throw new TransactionException("Invalid origin account.");
            }
        }
        if (destination == null) {
            if (!BankProperties.BANK_CODE.equals(destinationBank)) {
                Account account = new Account(null, destinationAccountNumber, null, destinationBank);
                destination = accountRepository.save(account);
            } else {
                throw new TransactionException("Invalid destination account.");
            }
        }
        return executeTransfer(origin, destination,transactionDTO);
    }

    @Override
    public TransactionDTO deposit(@NotNull TransactionDTO transactionDTO) throws TransactionException {
        if (transactionDTO.getType() != TransactionType.DEPOSIT) {
            throw new InvalidParameterException("Wrong transaction type.");
        }
        Integer destinationBank = transactionDTO.getDestinationAccountBank();
        if (!BankProperties.BANK_CODE.equals(destinationBank)) {
            throw new TransactionException("Invalid bank number.");
        }
        String destinationAccountNumber = transactionDTO.getDestinationAccount();
        Account destination = accountRepository.findByNumberAndBank(destinationAccountNumber, destinationBank);
        if (destination == null) {
            throw new EntityNotFoundException("Account not found");
        }
        destination.setBalance(destination.getBalance() + transactionDTO.getValue());
        transactionDTO.setDate(Instant.now());
        Transaction transaction =
                transactionRepository.save(new TransactionDtoToTransactionMapper().map(transactionDTO));
        return new TransactionToTransactionDtoMapper().map(transaction);
    }

    private TransactionDTO executeTransfer(
            Account origin,
            Account destination,
            TransactionDTO transactionDTO
    ) throws TransactionException {
        Double value = transactionDTO.getValue();
        if (origin.belongsToThisBank()) {
            if (origin.getBalance() < value) {
                throw new TransactionException("Insufficient balance.");
            }
            origin.setBalance(origin.getBalance() - value);
        }
        if (destination.belongsToThisBank()) {
            destination.setBalance(destination.getBalance() + value);
        }
        transactionDTO.setDate(Instant.now());
        Transaction transaction =
                transactionRepository.save(new TransactionDtoToTransactionMapper().map(transactionDTO));
        return new TransactionToTransactionDtoMapper().map(transaction);
    }
}
