package com.cajuzeiro.payment.domain.usecase;

import com.cajuzeiro.payment.adapter.repository.TransactionRepository;
import com.cajuzeiro.payment.adapter.usecase.TransactionUseCaseAdapter;
import com.cajuzeiro.payment.domain.entity.Transaction;
import io.vavr.Function1;
import io.vavr.Function5;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TransactionUseCase implements TransactionUseCaseAdapter {
    private TransactionRepository transactionRepository;

    public TransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Function5<Long, Float, Integer, String, LocalDateTime, Void> createTransaction() {
        return (accountId, amount, mcc, merchant, created_at) -> {
            transactionRepository.createTransaction(accountId, amount, mcc, merchant, created_at);
            return null;
        };
    }

    @Override
    public Function1<Long, Optional<Transaction>> getTransaction() {
        return (id) -> transactionRepository.getTransaction(id);
    }

    @Override
    public Function1<Long, List<Transaction>> getTransactionsFromAccount() {
        return (accountId) -> transactionRepository.getTransactionsByUserId(accountId);
    }

}
