package com.cajuzeiro.payment.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Transaction {

    public Transaction(Float amount, String merchant, String mcc, Long accountId) {
        this.amount = amount;
        this.merchant = merchant;
        this.mcc = mcc;
        this.accountId = accountId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Or another suitable strategy
    private Long id;
    private Float amount;

    @Column(name = "account_id")//preferi mapear assim para ganho de performance e maior simplicidade
    private Long accountId;
    private String merchant;
    private String mcc;
    private LocalDateTime createdAt;

    public Transaction(Long id, Float amount, Long accountId, String merchant, String mcc, LocalDateTime createdAt) {
        this.id = id;
        this.amount = amount;
        this.accountId = accountId;
        this.merchant = merchant;
        this.mcc = mcc;
        this.createdAt = createdAt;
    }

    public Transaction() {
    }

    public Float getAmount() {
        return amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getMcc() {
        return mcc;
    }

    public Long getId() {
        return id;
    }
}
