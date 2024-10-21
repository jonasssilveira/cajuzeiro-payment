package com.cajuzeiro.payment.ports.dto.input;

import com.cajuzeiro.payment.domain.entity.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.function.Function;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDTO {
    @JsonProperty("account") // Mapeia o campo JSON para a propriedade Java
    private Long accountId;
    @JsonProperty("total_amount")
    private Float totalAmount;
    @JsonProperty("merchant")
    String merchant;
    @JsonProperty("mcc")
    String mcc;
    @JsonProperty("id")
    Long id;


    public TransactionDTO(Float totalAmount, Long account, String merchant, String mcc) {
        this.totalAmount = totalAmount;
        this.accountId = account;
        this.merchant = merchant;
        this.mcc = mcc;
    }

    public TransactionDTO(Long id, Float totalAmount, Long account, String merchant, String mcc) {
        this.totalAmount = totalAmount;
        this.accountId = account;
        this.merchant = merchant;
        this.mcc = mcc;
    }

    public TransactionDTO() {
    }

    public Transaction toTransaction() {
        return new Transaction(totalAmount, merchant, mcc, accountId);
    }

    public static Function<Transaction, TransactionDTO> TransactionToTransactionDTO() {
        return transaction -> new TransactionDTO(transaction.getId(), transaction.getAmount(), transaction.getAccountId(), transaction.getMerchant(), transaction.getMcc());
    }

    public Long getAccount() {
        return accountId;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getMcc() {
        return mcc;
    }
}
