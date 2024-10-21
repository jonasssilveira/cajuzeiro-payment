package com.cajuzeiro.payment.domain.usecase;

import com.cajuzeiro.payment.adapter.repository.TransactionRepository;
import com.cajuzeiro.payment.adapter.repository.WalletRepository;
import com.cajuzeiro.payment.adapter.usecase.WalletUseCaseAdapter;
import com.cajuzeiro.payment.domain.enums.Estabelecimento;
import com.cajuzeiro.payment.ports.dto.input.TransactionDTO;
import com.cajuzeiro.payment.ports.dto.input.WalletDTO;
import io.vavr.Function1;

import java.time.LocalDateTime;

public class WalletUseCase implements WalletUseCaseAdapter {
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletUseCase(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Function1<TransactionDTO, Void> deposit() {
        return transactionDTO -> {
            transactionRepository.createTransaction(transactionDTO.getAccount(),
                    transactionDTO.getTotalAmount(),
                    Estabelecimento.getEstabelecimentoSimpleMMCCode(transactionDTO.getMcc()),
                    transactionDTO.getMerchant(),
                    LocalDateTime.now());
            walletRepository.deposit(transactionDTO.getAccount(),
                    transactionDTO.getTotalAmount(),
                    Estabelecimento.getEstabelecimentoSimpleMMCCode(transactionDTO.getMcc()),
                    LocalDateTime.now());
            return null;
        };
    }

    @Override
    public Function1<WalletDTO, Void> createWallet() {
        return walletDTO -> {
            walletRepository.createWallet(walletDTO.getTotalAmount(),
                    walletDTO.getAccountId(),
                    walletDTO.getCategoryId(),
                    LocalDateTime.now(),
                    LocalDateTime.now());
            return null;
        };
    }
}
