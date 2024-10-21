package com.cajuzeiro.payment.adapter.usecase;

import com.cajuzeiro.payment.ports.dto.input.OutputCode;
import com.cajuzeiro.payment.ports.dto.input.TransactionDTO;

import java.util.function.Function;

public interface AuthorizerUseCaseAdapter {
    Function<TransactionDTO, OutputCode> simpleAuthorizer();

    Function<TransactionDTO, OutputCode> fallbackAuthorizer();

    Function<TransactionDTO, OutputCode> nameAuthorizer();
}
