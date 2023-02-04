package com.example.s24278bank.repository;

import com.example.s24278bank.model.BankAccount;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountRepositoryTest {

    private final BankAccountRepository bankAccountRepository = new BankAccountRepository();

    @Test
    public void accountShouldBeCreated() {
        BankAccount tempBankAccount = new BankAccount(1, "Adam", "Kowalski", 100);

        bankAccountRepository.create(tempBankAccount);
        Optional<BankAccount> tempAccount = bankAccountRepository.findById(1);

        assertTrue(tempAccount.isPresent());
        assertEquals(tempBankAccount, tempAccount.get());
    }

    @Test
    public void accountShouldNotBeCreated() {
        Optional<BankAccount> tempBankAccount = bankAccountRepository.findById(199);

        assertFalse(tempBankAccount.isPresent());
    }
}