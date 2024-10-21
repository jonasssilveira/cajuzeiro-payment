package com.cajuzeiro.payment.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Category {

    @Id
    private Long id;

    private String name;

    public String getName() {
        return name;
    }
}
