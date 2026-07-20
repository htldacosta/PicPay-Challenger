package com.picpay_challenger.transaction;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
    @Id
    Long id,
    Long payer,
    Long payee,
    BigDecimal value,
    LocalDateTime createdAT) {
}
