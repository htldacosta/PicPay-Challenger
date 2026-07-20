package com.picpay_challenger.wallet;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record Wallet(
        @Id Long id,
        String fullName,
        Long cpf,
        String emaill,
        String password,
        int type,
        BigDecimal balance) {
}
