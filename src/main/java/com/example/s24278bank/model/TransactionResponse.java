package com.example.s24278bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private TransactionStatus transactionStatus;
    private int NewBalance;
}
