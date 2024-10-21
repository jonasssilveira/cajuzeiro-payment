package com.cajuzeiro.payment.domain.enums;

import java.util.Arrays;

import static java.util.Objects.isNull;

public enum Estabelecimento {
    FOOD(1, "5411", "5412"),
    MEAL(2, "5811", "5812"),
    CASH(3);
    String[] code;
    Integer mmcCode;

    Estabelecimento(Integer mmcCode, String... code) {
        this.code = code;
        this.mmcCode = mmcCode;
    }

    public String getCode() {
        return Arrays.stream(code).findFirst().orElse(null);
    }

    public Integer getMmcCode() {
        return mmcCode;
    }

    public static Estabelecimento fromMMCCode(Integer mmcCode) {
        return Arrays.stream(Estabelecimento.values())
                .filter(x -> x.getMmcCode().equals(mmcCode))
                .findFirst()
                .orElse(null);
    }

    public static Integer getEstabelecimentoSimpleMMCCode(String code) {
        return Arrays.stream(Estabelecimento.values())
                .filter(x -> !isNull(x.getCode()) && x.getCode().equals(code))
                .map(Estabelecimento::getMmcCode)
                .findAny()
                .orElse(null);
    }

    public static Estabelecimento getEstabelecimentoFallback(String code) {
        return Arrays.stream(Estabelecimento.values())
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .orElse(CASH);
    }

}
