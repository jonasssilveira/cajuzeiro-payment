package com.cajuzeiro.payment.ports.infrastructure.controller;

import com.cajuzeiro.payment.ports.dto.input.TransactionDTO;
import com.cajuzeiro.payment.ports.dto.input.WalletDTO;
import com.cajuzeiro.payment.ports.services.TransactionService;
import com.cajuzeiro.payment.ports.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/payment/adm")
public class AdmController {

    private final WalletService walletService;
    private final TransactionService transactionService;

    public AdmController(@Autowired WalletService walletService,
                         @Autowired TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @PostMapping("deposit")
    public ResponseEntity<Void> deposit(@RequestBody TransactionDTO transactionDTO) {
        return handleWalletDeposit(this.walletService.deposit(), transactionDTO);
    }

    @PostMapping("wallet")
    public ResponseEntity<Void> create(@RequestBody WalletDTO walletDTO) {
        return handleWalletCreate(this.walletService.create(), walletDTO);
    }

    @GetMapping("transaction/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable("id") Long id) {
        return handlerTransaction(this.transactionService.getTransaction(), id);
    }

    @GetMapping("transaction/by-account/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(@PathVariable Long accountId) {
        return handlerTransactions(this.transactionService.getTransactionsFromAccount(), accountId);
    }

    private ResponseEntity<Void> handleWalletDeposit(Function<TransactionDTO, Void> walletFunction, TransactionDTO transactionDTO) {
        return Optional.ofNullable(transactionDTO)
                .map(walletFunction)
                .map(ResponseEntity::ok)
                .stream()
                .findAny()
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<Void> handleWalletCreate(Function<WalletDTO, Void> walletFunction, WalletDTO walletDTO) {
        return Optional.ofNullable(walletDTO)
                .map(walletFunction)
                .map(ResponseEntity::ok)
                .stream()
                .findAny()
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<TransactionDTO> handlerTransaction(Function<Long, TransactionDTO> transactionFunction, Long id) {
        return Optional.ofNullable(id)
                .map(transactionFunction)
                .map(ResponseEntity::ok)
                .stream()
                .findAny()
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<List<TransactionDTO>> handlerTransactions(Function<Long, List<TransactionDTO>> transactionFunction, Long accountId) {
        return Optional.ofNullable(accountId)
                .map(transactionFunction)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ofNullable(Collections.emptyList()));

    }
}
