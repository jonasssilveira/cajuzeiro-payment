package com.cajuzeiro.payment.domain.usecase;

import com.cajuzeiro.payment.adapter.repository.TransactionRepository;
import com.cajuzeiro.payment.adapter.repository.WalletRepository;
import com.cajuzeiro.payment.domain.NameAuthorizer;
import com.cajuzeiro.payment.domain.entity.Transaction;
import com.cajuzeiro.payment.domain.enums.Estabelecimento;
import com.cajuzeiro.payment.ports.dto.input.OutputCode;
import io.vavr.Function1;
import io.vavr.control.Option;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.cajuzeiro.payment.ports.dto.input.OutputCode.APROVADA;

public class AuthorizerUseCase {

    WalletRepository walletRepository;
    TransactionRepository transactionRepository;

    public AuthorizerUseCase(TransactionRepository transactionRepository,
                             WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public Function1<Transaction, OutputCode> simpleAuthorizer() {
        return t ->
                Option.of(t)
                        .map(tx -> this.walletRepository.getBenefits(tx.getAccountId(), Estabelecimento.getEstabelecimentoSimpleMMCCode(t.getMcc())))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .filter(w -> w.getTotalAmount() >= t.getAmount())
                        .map(wallet -> {
                            this.transactionRepository.createTransaction(t.getAccountId(), t.getAmount(), Estabelecimento.getEstabelecimentoSimpleMMCCode(t.getMcc()), t.getMerchant(), LocalDateTime.now());
                            this.walletRepository.withdraw(t.getAccountId(), t.getAmount(), Estabelecimento.getEstabelecimentoSimpleMMCCode(t.getMcc()), LocalDateTime.now());
                            return APROVADA;
                        })
                        .getOrElse(OutputCode.REJEITADA);
    }

    public Function1<Transaction, OutputCode> fallbackAuthorizer() {
        return t -> Option.of(t)
                .flatMap(tx -> Option.of(this.walletRepository.getBenefits(
                        t.getAccountId(),
                        Estabelecimento.getEstabelecimentoFallback(t.getMcc()).getMmcCode()
                )))
                .filter(wallet -> wallet.get().getTotalAmount() >= t.getAmount())
                .map(wallet -> {
                    this.transactionRepository.createTransaction(
                            t.getAccountId(),
                            t.getAmount(),
                            Estabelecimento.getEstabelecimentoSimpleMMCCode(t.getMcc()),
                            t.getMerchant(),
                            LocalDateTime.now()
                    );
                    this.walletRepository.withdraw(
                            t.getAccountId(),
                            t.getAmount(),
                            Estabelecimento.getEstabelecimentoSimpleMMCCode(t.getMcc()),
                            LocalDateTime.now()
                    );
                    return OutputCode.APROVADA;
                })
                .getOrElse(() ->
                        Option.of(this.walletRepository.getCash(t.getAccountId()))
                                .filter(wallet -> wallet.get().getTotalAmount() >= t.getAmount())
                                .map(wallet -> {
                                    this.transactionRepository.createTransaction(
                                            t.getAccountId(),
                                            t.getAmount(),
                                            Estabelecimento.CASH.getMmcCode(),
                                            t.getMerchant(),
                                            LocalDateTime.now()
                                    );
                                    this.walletRepository.withdraw(
                                            t.getAccountId(),
                                            t.getAmount(),
                                            Estabelecimento.CASH.getMmcCode(),
                                            LocalDateTime.now()
                                    );
                                    return OutputCode.APROVADA;
                                })
                                .getOrElse(OutputCode.REJEITADA)
                );
    }

    public Function1<Transaction, OutputCode> nameAuthorizer() {
        return t -> Option.of(t)
                .map(Transaction::getMerchant)
                .map(NameAuthorizer::getEstabelecimentoByName)
                .map(walletCode -> this.walletRepository.getBenefits(t.getAccountId(), walletCode))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(w -> w.getTotalAmount() >= t.getAmount())
                .map(wallet -> {
                    this.transactionRepository.createTransaction(t.getAccountId(), t.getAmount(), Estabelecimento.fromMMCCode(wallet.getCategoryId()).getMmcCode(), t.getMerchant(), LocalDateTime.now());
                    this.walletRepository.withdraw(t.getAccountId(), t.getAmount(), Estabelecimento.fromMMCCode(wallet.getCategoryId()).getMmcCode(),LocalDateTime.now());
                    return APROVADA;
                })
                .getOrElse(OutputCode.REJEITADA);
    }
}
