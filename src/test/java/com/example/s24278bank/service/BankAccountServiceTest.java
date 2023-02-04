package com.example.s24278bank.service;

import com.example.s24278bank.exception.NotFoundException;
import com.example.s24278bank.exception.ValidationException;
import com.example.s24278bank.model.BankAccount;
import com.example.s24278bank.model.TransactionStatus;
import com.example.s24278bank.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BankAccountServiceTest {

    private final BankAccountRepository bankAccountRepository = mock(BankAccountRepository.class);
    private final BankAccountService bankAccountService = new BankAccountService(bankAccountRepository);


    @Test
    public void accountShouldBeCreated() {
        BankAccount bankAccount = new BankAccount(2, "Anna", "Kowalska", 1000);

        assertDoesNotThrow(() -> bankAccountService.create(bankAccount));
    }

    @ParameterizedTest
    @MethodSource("bankAccountProvider")
    public void accountShouldNotBeCreated(BankAccount bankAccount) {
        ValidationException validationException = assertThrows(ValidationException.class, () -> bankAccountService.create(bankAccount));

        assertEquals("Incorrect data", validationException.getMessage());
    }

    public static Stream<Arguments> bankAccountProvider() {
        return Stream.of(
                Arguments.of(new BankAccount(4, null, "Kowalski", 100)),
                Arguments.of(new BankAccount(5, "Adam", null, 100)),
                Arguments.of(new BankAccount(6, null, "Kowalski", -100))
        );
    }

    @Test
    public void accountShouldBeGotById() throws NotFoundException {
        BankAccount bankAccount = new BankAccount(5, "Adam", "Kowalski", 100);

        when(bankAccountRepository.findById(5)).thenReturn(Optional.of(bankAccount));

        assertEquals(bankAccount, bankAccountService.getById(5));
    }

    @Test
    public void accountShouldNotBeGotById() {
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> bankAccountService.getById(666));

        assertEquals("Bank account not found", notFoundException.getMessage());
    }

    @ParameterizedTest
    @MethodSource("withdrawProvider")
    public void withdrawTest(TransactionStatus expected, int amount) {
        BankAccount bankAccount = new BankAccount(7,  "Adam", "Kowalski",100);

        assertEquals( expected, bankAccountService.withdraw(bankAccount, amount));
    }

    public static Stream<Arguments> withdrawProvider() {
        return Stream.of(
                Arguments.of(TransactionStatus.DECLINED, -100),
                Arguments.of(TransactionStatus.DECLINED, 1000),
                Arguments.of(TransactionStatus.ACCEPTED, 50));
    }

    @ParameterizedTest
    @MethodSource("depositProvider")
    public void depositTest(TransactionStatus expected, int amount) {
        BankAccount bankAccount = new BankAccount(8, "Adam", "Kowalski",100);

        assertEquals( expected, bankAccountService.withdraw(bankAccount, amount));
    }

    public static Stream<Arguments> depositProvider() {
        return Stream.of(
                Arguments.of(TransactionStatus.DECLINED, -100),
                Arguments.of(TransactionStatus.ACCEPTED, 50));
    }
}