package com.cajuzeiro.payment.ports.infrastructure.controller;

import com.cajuzeiro.payment.ports.dto.input.OutputCode;
import com.cajuzeiro.payment.ports.dto.input.TransactionDTO;
import com.cajuzeiro.payment.ports.services.AuthorizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/v1/authorizer")
public class AuthorizerController {

    private final AuthorizerService authorizerService;

    public AuthorizerController(@Autowired AuthorizerService authorizerService) {
        this.authorizerService = authorizerService;
    }

    @PostMapping("simple")
    public ResponseEntity<OutputCode> simplePayment(@RequestBody TransactionDTO transactionDTO){
        return processPayment(authorizerService.simpleAuthorizer(), transactionDTO);
    }

    @PostMapping("fallback")
    public ResponseEntity<OutputCode> fallbackAuthorizer(@RequestBody TransactionDTO transactionDTO){
        return processPayment(authorizerService.fallbackAuthorizer(), transactionDTO);
    }

    @PostMapping("name")
    public ResponseEntity<OutputCode> nameAuthorizer(@RequestBody TransactionDTO transactionDTO){
        return processPayment(authorizerService.nameAuthorizer(), transactionDTO);
    }

    private ResponseEntity<OutputCode> processPayment(Function<TransactionDTO, OutputCode> paymentFunction, TransactionDTO transactionDTO){
        return Optional.ofNullable(transactionDTO)
                .map(paymentFunction)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Payment failed"));
    }

}