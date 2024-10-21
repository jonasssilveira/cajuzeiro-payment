package com.cajuzeiro.payment.ports.dto.input;

public enum OutputCode {
    APROVADA("00"),
    REJEITADA("51"),
    ERROR("07");

    private String returnCode;

    OutputCode(String returnCode) {
        this.returnCode = returnCode;
    }

}
