package com.cajuzeiro.payment.domain.usecase;

import com.cajuzeiro.payment.adapter.repository.AccountRepository;
import com.cajuzeiro.payment.ports.dto.input.AccountDTO;
import io.vavr.control.Option;

import java.util.function.Function;

public class AccountUsecase {
    private final AccountRepository accountRepository;

    public AccountUsecase(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public Function<AccountDTO, Void> createAccount() {
        return account -> (Void) Option.of(account)
                .map(AccountDTO::toAccount)
                .peek(acc -> this.accountRepository.createAccount(acc.getUsername()))
                .toJavaOptional()
                .map(a -> null)
                .orElse(null);
    }
}
