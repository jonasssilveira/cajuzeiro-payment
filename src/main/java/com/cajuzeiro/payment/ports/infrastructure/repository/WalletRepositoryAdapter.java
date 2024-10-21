package com.cajuzeiro.payment.ports.infrastructure.repository;

import com.cajuzeiro.payment.adapter.repository.WalletRepository;
import com.cajuzeiro.payment.domain.entity.Account;
import com.cajuzeiro.payment.domain.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WalletRepositoryAdapter extends JpaRepository<Account, Long>, WalletRepository {
    @Modifying
    @Transactional
    @Query("UPDATE Wallet w SET w.totalAmount = w.totalAmount - :amount, w.updatedAt=:now where w.accountId = :accountId AND w.categoryId =:mmc")
    void withdraw(@Param("accountId") Long accountId, @Param("amount") Float amount, @Param("mmc") Integer mmc, @Param("now")LocalDateTime now);

    @Modifying
    @Transactional
    @Query("UPDATE Wallet w SET w.totalAmount = w.totalAmount + :amount, w.updatedAt=:now where w.accountId = :accountId AND w.categoryId =:mmc")
    void deposit(@Param("accountId") Long accountId,@Param("amount") Float amount, @Param("mmc") Integer mmc, @Param("now")LocalDateTime now);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Wallet (total_amount, account_id, category_id, created_at, updated_at) VALUES (:total_amount,:account_id, :category_id, :createdAt, :updatedAt)", nativeQuery = true)
    void createWallet(@Param("total_amount") Float totalAmount,@Param("account_id") Long accountId,@Param("category_id") Integer categoryId, @Param("createdAt")LocalDateTime createdAt, @Param("updatedAt")LocalDateTime updatedAt);

    @Query("SELECT w from Wallet w where w.accountId = :accountId AND w.categoryId =:mmc")
    Optional<Wallet> getBenefits(@Param("accountId") Long accountId, @Param("mmc") Integer mmc);

    @Query("SELECT w from Wallet w where w.accountId = :accountId AND w.categoryId = 3")
    Optional<Wallet> getCash(@Param("accountId") Long accountId);

}
