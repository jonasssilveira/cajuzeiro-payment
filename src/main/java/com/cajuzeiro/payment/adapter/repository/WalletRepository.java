package com.cajuzeiro.payment.adapter.repository;

import com.cajuzeiro.payment.domain.entity.Wallet;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WalletRepository {
    void withdraw(Long accountId, Float amount, Integer mmc, LocalDateTime updatedAt);
    void deposit(Long accountId, Float amount, Integer mmc, LocalDateTime updatedAt);
    Optional<Wallet> getBenefits(Long id, Integer mmc);
    Optional<Wallet> getCash(Long id);
    void createWallet(Float totalAmount, Long accountId, Integer categoryId,LocalDateTime createdAt, LocalDateTime updatedAt);
}
