package com.picpay_challenger.wallet;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.math.BigDecimal;


public record Wallet(
        @Id Long id,
        String fullName,
        Long cpf,
        String email,
        String password,
        int type,
        BigDecimal balance,
        @Version Long version) {

    public Wallet debit(BigDecimal value) {
        return new Wallet(id, fullName, cpf, email, password, type,
                balance.subtract(value), version);
    }

    public Wallet credit(BigDecimal value) {
        return new Wallet(id, fullName, cpf, email, password, type, balance.add(value), version);
    }
}
