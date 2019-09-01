package com.bank.mapper;

import com.bank.data.entity.Account;
import com.bank.dto.AccountDTO;

import javax.validation.constraints.NotNull;

public class AccountToAccountDtoMapper implements Mapper<Account, AccountDTO> {
    @Override
    public AccountDTO map(@NotNull Account from) {
        return new AccountDTO(from.getId(), from.getNumber(), from.getBalance(), from.getBank());
    }
}
