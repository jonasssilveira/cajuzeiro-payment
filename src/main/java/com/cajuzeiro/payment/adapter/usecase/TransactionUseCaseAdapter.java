package com.cajuzeiro.payment.adapter.usecase;

import com.cajuzeiro.payment.domain.entity.Transaction;
import com.cajuzeiro.payment.ports.dto.input.TransactionDTO;
import io.vavr.Function1;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface TransactionUseCaseAdapter {
    Function1<Long, Optional<Transaction>> getTransaction();

    Function1<Long, List<Transaction>> getTransactionsFromAccount();

}
