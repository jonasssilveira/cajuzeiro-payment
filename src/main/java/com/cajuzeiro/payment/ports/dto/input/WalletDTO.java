package com.cajuzeiro.payment.ports.dto.input;

import com.cajuzeiro.payment.domain.entity.Transaction;
import com.cajuzeiro.payment.domain.entity.Wallet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

import java.util.function.Function;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WalletDTO {
    @Column(name = "total_amount")
    private Float totalAmount;

    @Column(name = "account_id")
    private Long accountId;

    @JsonProperty("category_id")
    private Integer categoryId;

    public WalletDTO(Float totalAmount, Long accountId, Integer categoryId) {
        this.totalAmount = totalAmount;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }

    public static Function<Wallet, WalletDTO> walletToWalletDTO() {
        return wallet -> new WalletDTO(wallet.getTotalAmount(), wallet.getAccountId(), wallet.getCategoryId());
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}
