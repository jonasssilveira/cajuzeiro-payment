package com.cajuzeiro.payment.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Wallet {
    @Id
    private Long id;

    @Column(name = "total_amount")
    private Float totalAmount;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "category_id")
    private Integer categoryId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Wallet() {
    }

    public Wallet(Float totalAmount, Long accountId, Integer categoryId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.totalAmount = totalAmount;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Wallet(Long id, Float totalAmount, Long accountId, Integer categoryId) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
