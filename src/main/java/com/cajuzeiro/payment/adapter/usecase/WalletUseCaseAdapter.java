package com.cajuzeiro.payment.adapter.usecase;

import com.cajuzeiro.payment.ports.dto.input.TransactionDTO;
import com.cajuzeiro.payment.ports.dto.input.WalletDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public interface WalletUseCaseAdapter {
    Function<TransactionDTO, Void> deposit();

    Function<WalletDTO, Void> createWallet();
}
