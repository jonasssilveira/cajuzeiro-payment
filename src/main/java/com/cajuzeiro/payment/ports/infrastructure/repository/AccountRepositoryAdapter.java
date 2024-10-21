package com.cajuzeiro.payment.ports.infrastructure.repository;

import com.cajuzeiro.payment.adapter.repository.AccountRepository;
import com.cajuzeiro.payment.domain.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepositoryAdapter extends JpaRepository<Account, Long>, AccountRepository {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO public.account (username, created_at, updated_at) VALUES (:username, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)", nativeQuery = true)
    void createAccount(@Param("username") String username);

}
