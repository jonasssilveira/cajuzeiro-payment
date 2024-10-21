package com.cajuzeiro.payment.ports.services;

import com.cajuzeiro.payment.adapter.repository.TransactionRepository;
import com.cajuzeiro.payment.adapter.repository.WalletRepository;
import com.cajuzeiro.payment.adapter.usecase.WalletUseCaseAdapter;
import com.cajuzeiro.payment.domain.usecase.WalletUseCase;
import com.cajuzeiro.payment.ports.dto.input.TransactionDTO;
import com.cajuzeiro.payment.ports.dto.input.WalletDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class WalletService {

    private final WalletUseCaseAdapter walletUseCase;

    public WalletService(@Autowired TransactionRepository transactionRepository,
                         @Autowired WalletRepository walletRepository) {
        this.walletUseCase = new WalletUseCase(walletRepository, transactionRepository);
    }

    public Function<TransactionDTO, Void> deposit() {
        return dto -> this.walletUseCase.deposit().apply(dto);
    }

    public Function<WalletDTO, Void> create() {
        return dto -> this.walletUseCase.createWallet().apply(dto);
    }
}
