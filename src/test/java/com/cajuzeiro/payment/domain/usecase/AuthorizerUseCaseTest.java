package com.cajuzeiro.payment.domain.usecase;

import com.cajuzeiro.payment.adapter.repository.TransactionRepository;
import com.cajuzeiro.payment.adapter.repository.WalletRepository;
import com.cajuzeiro.payment.domain.entity.Transaction;
import com.cajuzeiro.payment.domain.entity.Wallet;
import com.cajuzeiro.payment.domain.enums.Estabelecimento;
import io.vavr.test.Arbitrary;
import io.vavr.test.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.cajuzeiro.payment.ports.dto.input.OutputCode.APROVADA;
import static com.cajuzeiro.payment.ports.dto.input.OutputCode.REJEITADA;
import static org.mockito.Mockito.when;

public class AuthorizerUseCaseTest {
    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private AuthorizerUseCase authorizerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Cria uma instância real de AuthorizerUseCase usando os mocks de repositórios.
        authorizerUseCase = new AuthorizerUseCase(transactionRepository, walletRepository);
    }

    @Test
    void testSimpleAuthorizer_Approved_If_Amount_Bigger_Than_Balance() {
        // Gerador de transações aleatórias com valores entre 1.0 e 1000.0
        Arbitrary<Transaction> transactions =  Arbitrary.of(1,2,3,4,5,6,7,8,9,10).map(accountId ->
                new Transaction(
                        Float.parseFloat(Integer.valueOf(accountId*100).toString()),
                        "Some Merchant",
                        "5411", // MCC que mapeia para Estabelecimento.FOOD ou MEAL
                        accountId.longValue()
                ));

        // Gerador de valores para saldo da carteira, garantindo que seja maior que o valor da transação
        Property.def("A transação deve ser aprovada se o saldo for maior que o valor da transação")
                .forAll(transactions)
                .suchThat(transaction -> {
                    // Mocka o comportamento do repositório da carteira
                    when(walletRepository.getBenefits(transaction.getAccountId(), Estabelecimento.getEstabelecimentoSimpleMMCCode(transaction.getMcc())))
                            .thenReturn(Optional.of(new Wallet(1L, 30000F, 1L, 1)));

                    // Invoca o método de autorização
                    return authorizerUseCase.simpleAuthorizer()
                            .apply(transaction)
                            .equals(APROVADA);
                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void testSimpleAuthorizer_denies_If_Amount_Less_Than_Balance() {
        // Gerador de transações aleatórias com valores entre 1.0 e 1000.0
        Arbitrary<Transaction> transactions =  Arbitrary.of(1,2,3,4,5,6,7,8,9,10).map(accountId ->
                new Transaction(
                        Float.parseFloat(Integer.valueOf(accountId*100).toString()),
                        "Some Merchant",
                        "5411", // MCC que mapeia para Estabelecimento.FOOD ou MEAL
                        accountId.longValue()
                ));

        // Gerador de valores para saldo da carteira, garantindo que seja maior que o valor da transação
        Property.def("A transação deve ser rejeitada se o saldo for menor que o valor da transação")
                .forAll(transactions)
                .suchThat(transaction -> {
                    // Mocka o comportamento do repositório da carteira
                    when(walletRepository.getBenefits(transaction.getAccountId(), Estabelecimento.getEstabelecimentoSimpleMMCCode(transaction.getMcc())))
                            .thenReturn(Optional.of(new Wallet(1L, 30F, 1L, 1)));

                    // Invoca o método de autorização
                    return authorizerUseCase.simpleAuthorizer()
                            .apply(transaction)
                            .equals(REJEITADA);
                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void testSimpleAuthorizer_denies_If_Benefits_Not_Found() {
        // Gerador de transações aleatórias com valores entre 1.0 e 1000.0
        Arbitrary<Transaction> transactions =  Arbitrary.of(1,2,3,4,5,6,7,8,9,10).map(accountId ->
                new Transaction(
                        Float.parseFloat(Integer.valueOf(accountId*100).toString()),
                        "Some Merchant",
                        "1234", // MCC que mapeia para Estabelecimento.FOOD ou MEAL
                        accountId.longValue()
                ));

        // Gerador de valores para saldo da carteira, garantindo que seja maior que o valor da transação
        Property.def("A transação deve ser rejeitada se o beneficio não for achado")
                .forAll(transactions)
                .suchThat(transaction -> {
                    return authorizerUseCase.simpleAuthorizer()
                            .apply(transaction)
                            .equals(REJEITADA);
                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void testFallbackAuthorizer_Approved_If_Cash_Amount_Is_Bigger_Than_Balance() {
        // Gerador de transações aleatórias com valores entre 1.0 e 1000.0
        Arbitrary<Transaction> transactions =  Arbitrary.of(1,2,3,4,5,6,7,8,9,10).map(accountId ->
                new Transaction(
                        Float.parseFloat(Integer.valueOf(accountId*100).toString()),
                        "Some Merchant",
                        "5411", // MCC que mapeia para Estabelecimento.FOOD ou MEAL
                        accountId.longValue()
                ));

        // Gerador de valores para saldo da carteira, garantindo que seja maior que o valor da transação
        Property.def("A transação deve ser aprovada se o saldo do CASH for maior que o valor da transação")
                .forAll(transactions)
                .suchThat(transaction -> {
                    // Mocka o comportamento do repositório da carteira
                    when(walletRepository.getBenefits(transaction.getAccountId(), Estabelecimento.getEstabelecimentoSimpleMMCCode(transaction.getMcc())))
                            .thenReturn(Optional.of(new Wallet(1L, 30F, 1L, 1)));
                    when(walletRepository.getCash(transaction.getAccountId()))
                            .thenReturn(Optional.of(new Wallet(1L, 30000F, 1L, 3)));

                    // Invoca o método de autorização
                    return authorizerUseCase.fallbackAuthorizer()
                            .apply(transaction)
                            .equals(APROVADA);
                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void testFallbackAuthorizer_Approved_If_Benefits_Amount_Is_Bigger_Than_Balance() {
        // Gerador de transações aleatórias com valores entre 1.0 e 1000.0
        Arbitrary<Transaction> transactions =  Arbitrary.of(1,2,3,4,5,6,7,8,9,10).map(accountId ->
                new Transaction(
                        Float.parseFloat(Integer.valueOf(accountId*100).toString()),
                        "Some Merchant",
                        "5411", // MCC que mapeia para Estabelecimento.FOOD ou MEAL
                        accountId.longValue()
                ));

        // Gerador de valores para saldo da carteira, garantindo que seja maior que o valor da transação
        Property.def("A transação deve ser aprovada se o saldo do beneficio for maior que o valor da transação")
                .forAll(transactions)
                .suchThat(transaction -> {
                    // Mocka o comportamento do repositório da carteira
                    when(walletRepository.getBenefits(transaction.getAccountId(), Estabelecimento.getEstabelecimentoSimpleMMCCode(transaction.getMcc())))
                            .thenReturn(Optional.of(new Wallet(1L, 30000F, 1L, 1)));
                    // Invoca o método de autorização
                    return authorizerUseCase.fallbackAuthorizer()
                            .apply(transaction)
                            .equals(APROVADA);
                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void testFallbackAuthorizer_Rejected_If_Amount_Less_Than_Balance() {
        // Gerador de transações aleatórias com valores entre 1.0 e 1000.0
        Arbitrary<Transaction> transactions =  Arbitrary.of(1,2,3,4,5,6,7,8,9,10).map(accountId ->
                new Transaction(
                        Float.parseFloat(Integer.valueOf(accountId*100).toString()),
                        "Some Merchant",
                        "5411", // MCC que mapeia para Estabelecimento.FOOD ou MEAL
                        accountId.longValue()
                ));

        // Gerador de valores para saldo da carteira, garantindo que seja maior que o valor da transação
        Property.def("A transação deve ser rejeitada se o saldo do beneficio for menor que o valor da transação")
                .forAll(transactions)
                .suchThat(transaction -> {
                    // Mocka o comportamento do repositório da carteira
                    when(walletRepository.getBenefits(transaction.getAccountId(), Estabelecimento.getEstabelecimentoSimpleMMCCode(transaction.getMcc())))
                            .thenReturn(Optional.of(new Wallet(1L, 30F, 1L, 1)));
                    when(walletRepository.getCash(transaction.getAccountId()))
                            .thenReturn(Optional.of(new Wallet(1L, 30F, 1L, 3)));

                    // Invoca o método de autorização
                    return authorizerUseCase.fallbackAuthorizer()
                            .apply(transaction)
                            .equals(REJEITADA);
                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void testNameAuthorizer_Approved_If_Benefits_Amount_Is_Bigger_Than_Balance() {
        // Gerador de transações aleatórias com valores entre 1.0 e 1000.0
        Arbitrary<Transaction> transactions =  Arbitrary.of(1,2,3,4,5,6,7,8,9,10).map(accountId ->
                new Transaction(
                        Float.parseFloat(Integer.valueOf(accountId*100).toString()),
                        "marcenaria do zé",
                        "5411", // MCC que mapeia para Estabelecimento.FOOD ou MEAL
                        accountId.longValue()
                ));

        // Gerador de valores para saldo da carteira, garantindo que seja maior que o valor da transação
        Property.def("A transação deve ser aprovada se o saldo do beneficio for encontrado e maior que o valor da transação")
                .forAll(transactions)
                .suchThat(transaction -> {
                    // Mocka o comportamento do repositório da carteira
                    when(walletRepository.getBenefits(transaction.getAccountId(), Estabelecimento.getEstabelecimentoSimpleMMCCode(transaction.getMcc())))
                            .thenReturn(Optional.of(new Wallet(1L, 300000F, 1L, 1)));

                    // Invoca o método de autorização
                    return authorizerUseCase.nameAuthorizer()
                            .apply(transaction)
                            .equals(APROVADA);
                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void testNameAuthorizer_Reject_If_Benefits_Amount_Is_Less_Than_Balance() {
        // Gerador de transações aleatórias com valores entre 1.0 e 1000.0
        Arbitrary<Transaction> transactions =  Arbitrary.of(1,2,3,4,5,6,7,8,9,10).map(accountId ->
                new Transaction(
                        Float.parseFloat(Integer.valueOf(accountId*100).toString()),
                        "Padaria do zé",
                        "5411", // MCC que mapeia para Estabelecimento.FOOD ou MEAL
                        accountId.longValue()
                ));

        // Gerador de valores para saldo da carteira, garantindo que seja maior que o valor da transação
        Property.def("A transação deve ser rejeitada se o saldo do beneficio for menor que o valor da transação")
                .forAll(transactions)
                .suchThat(transaction -> {
                    // Mocka o comportamento do repositório da carteira
                    when(walletRepository.getBenefits(transaction.getAccountId(), Estabelecimento.getEstabelecimentoSimpleMMCCode(transaction.getMcc())))
                            .thenReturn(Optional.of(new Wallet(1L, 30F, 1L, 1)));

                    // Invoca o método de autorização
                    return authorizerUseCase.nameAuthorizer()
                            .apply(transaction)
                            .equals(REJEITADA);
                })
                .check()
                .assertIsSatisfied();
    }

    @Test
    void testNameAuthorizer_Reject_If_Name_Not_Mapped() {
        // Gerador de transações aleatórias com valores entre 1.0 e 1000.0
        Arbitrary<Transaction> transactions =  Arbitrary.of(1,2,3,4,5,6,7,8,9,10).map(accountId ->
                new Transaction(
                        Float.parseFloat(Integer.valueOf(accountId*100).toString()),
                        "podrao do zé",
                        "5411", // MCC que mapeia para Estabelecimento.FOOD ou MEAL
                        accountId.longValue()
                ));

        // Gerador de valores para saldo da carteira, garantindo que seja maior que o valor da transação
        Property.def("A transação deve ser rejeitada se o saldo do beneficio for menor que o valor da transação")
                .forAll(transactions)
                .suchThat(transaction -> {
                    // Mocka o comportamento do repositório da carteira
                    when(walletRepository.getBenefits(transaction.getAccountId(), Estabelecimento.getEstabelecimentoSimpleMMCCode(transaction.getMcc())))
                            .thenReturn(Optional.of(new Wallet(1L, 30000F, 1L, 1)));

                    // Invoca o método de autorização
                    return authorizerUseCase.nameAuthorizer()
                            .apply(transaction)
                            .equals(REJEITADA);
                })
                .check()
                .assertIsSatisfied();
    }

}