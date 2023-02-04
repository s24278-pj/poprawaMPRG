package com.example.s24278bank.service;

import com.example.s24278bank.exception.NotFoundException;
import com.example.s24278bank.exception.ValidationException;
import com.example.s24278bank.model.BankAccount;
import com.example.s24278bank.model.TransactionStatus;
import com.example.s24278bank.repository.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public void create(BankAccount bankAccount) throws ValidationException {
        if (bankAccount == null
                || bankAccount.getClientName() == null
                || bankAccount.getClientSurname() == null
                || bankAccount.getBalance() < 0) {
            throw new ValidationException("Incorrect data");
        }
        bankAccountRepository.create(bankAccount);
    }

    public List<BankAccount> getAll(){
        return bankAccountRepository.getAll();
    }

    public BankAccount getById(int id) throws NotFoundException {
        Optional<BankAccount> tempBankAccount = bankAccountRepository.findById(id);
        if (tempBankAccount.isPresent()) {
            return tempBankAccount.get();
        }
        throw new NotFoundException("Bank account not found");
    }

    public TransactionStatus withdraw(BankAccount bankAccount, int amount) {
        if (amount <= 0) {
            return TransactionStatus.DECLINED;
        }
        if (amount > bankAccount.getBalance()) {
            return TransactionStatus.DECLINED;
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        return TransactionStatus.ACCEPTED;
    }

    public TransactionStatus deposit(BankAccount bankAccount, int amount) {
        if (amount <= 0) {
            return TransactionStatus.DECLINED;
        }
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        return TransactionStatus.ACCEPTED;
    }
}
