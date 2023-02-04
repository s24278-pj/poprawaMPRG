package com.example.s24278bank.controller;

import com.example.s24278bank.model.BankAccount;
import com.example.s24278bank.repository.BankAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BankAccountControllerITTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanup() {
        bankAccountRepository.removeAll();
    }

    @Test
    void registerNewBankAccount() throws Exception {
        BankAccount bankAccount = new BankAccount(1, "Anna", "Kowalska", 1000);
        bankAccountRepository.create(bankAccount);

    }
}