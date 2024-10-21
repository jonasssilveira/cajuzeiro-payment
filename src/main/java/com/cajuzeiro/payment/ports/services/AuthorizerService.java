package com.cajuzeiro.payment.ports.services;

import com.cajuzeiro.payment.adapter.repository.TransactionRepository;
import com.cajuzeiro.payment.adapter.repository.WalletRepository;
import com.cajuzeiro.payment.domain.usecase.AuthorizerUseCase;
import com.cajuzeiro.payment.ports.dto.input.OutputCode;
import com.cajuzeiro.payment.ports.dto.input.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AuthorizerService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerUseCase paymentUseCase;

    public AuthorizerService(@Autowired TransactionRepository transactionRepository,
                             @Autowired WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.paymentUseCase = new AuthorizerUseCase(transactionRepository, walletRepository);
    }

    public Function<TransactionDTO, OutputCode> simpleAuthorizer() {
        return dto -> this.paymentUseCase.simpleAuthorizer().apply(dto.toTransaction());
    }

    public Function<TransactionDTO, OutputCode> fallbackAuthorizer() {
        return dto -> this.paymentUseCase.fallbackAuthorizer().apply(dto.toTransaction());
    }

    public Function<TransactionDTO, OutputCode> nameAuthorizer() {
        return dto -> this.paymentUseCase.nameAuthorizer().apply(dto.toTransaction());
    }
}
