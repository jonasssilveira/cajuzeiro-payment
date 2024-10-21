package com.cajuzeiro.payment.domain;

import com.cajuzeiro.payment.domain.enums.Estabelecimento;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface NameAuthorizer {
    Map<Estabelecimento, Set<String>> authorize = new HashMap<>() {{
        put(Estabelecimento.MEAL, Set.of("eat", "restau", "refei", "padaria"));
        put(Estabelecimento.FOOD, Set.of("store", "mercad", "marcenaria"));
    }};

    static Integer getEstabelecimentoByName(String name) {
        return authorize.entrySet()
                .stream()
                .filter(entry -> entry.getValue()
                        .stream()
                        .anyMatch(keyword -> name.toLowerCase().contains(keyword)))
                .map(entry -> entry.getKey().getMmcCode())
                .findFirst()
                .orElse(Estabelecimento.CASH.getMmcCode());
    }
}
