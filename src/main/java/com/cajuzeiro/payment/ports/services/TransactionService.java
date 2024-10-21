package com.cajuzeiro.payment.ports.services;

import com.cajuzeiro.payment.adapter.repository.TransactionRepository;
import com.cajuzeiro.payment.adapter.usecase.TransactionUseCaseAdapter;
import com.cajuzeiro.payment.domain.usecase.TransactionUseCase;
import com.cajuzeiro.payment.ports.dto.input.TransactionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    TransactionUseCaseAdapter transactionUseCase;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionUseCase = new TransactionUseCase(transactionRepository);
    }

    public Function<Long, TransactionDTO> getTransaction() {
        return id -> this.transactionUseCase.getTransaction()
                .apply(id)
                .map(t -> TransactionDTO.TransactionToTransactionDTO().apply(t))
                .orElse(null);
    }

    public Function<Long, List<TransactionDTO>> getTransactionsFromAccount() {
        return accountId -> this.transactionUseCase.getTransactionsFromAccount().apply(accountId)
                .stream()
                .map(t -> TransactionDTO.TransactionToTransactionDTO().apply(t))
                .collect(Collectors.toList());
    }
}
