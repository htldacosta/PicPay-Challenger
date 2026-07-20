package com.picpay_challenger.transaction;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("TRANSACTIONS")
public record Transaction(
    @Id Long id,
    Long payer,
    Long payee,
    BigDecimal value,
    @CreatedDate LocalDateTime createdAT) {

    public Transaction {
        value = value.setScale(2);
    }
}

