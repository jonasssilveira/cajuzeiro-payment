package com.cajuzeiro.payment.ports.infrastructure.repository;

import com.cajuzeiro.payment.adapter.repository.TransactionRepository;
import com.cajuzeiro.payment.domain.entity.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepositoryAdapter extends JpaRepository<Transaction, Long>, TransactionRepository {
    @Query("SELECT t FROM Transaction t WHERE t.id=:id")
    Optional<Transaction> getTransaction(@Param("id") Long id);

    @Query(value = "SELECT t.* FROM transaction t WHERE t.account_id = :id", nativeQuery = true)
    List<Transaction> getTransactionsByUserId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO transaction (account_id, amount, mcc, merchant, created_at) VALUES (:accountId, :amount, :mcc, :merchant, :created_at)", nativeQuery = true)
    void createTransaction(@Param("accountId")Long accountId, @Param("amount")Float amount, @Param("mcc")Integer mcc, @Param("merchant")String merchant, @Param("created_at")LocalDateTime created_at);

}
