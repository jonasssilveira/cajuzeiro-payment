package com.cajuzeiro.payment.adapter.repository;

import com.cajuzeiro.payment.domain.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    void createTransaction(Long accountId, Float amount, Integer mcc, String merchant, LocalDateTime created_at);
    Optional<Transaction> getTransaction(Long id);
    List<Transaction> getTransactionsByUserId(Long id);
}
