package com.example.s24278bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankAccount {
    private final int id;
    private String clientName;
    private String clientSurname;
    private int balance;
}
