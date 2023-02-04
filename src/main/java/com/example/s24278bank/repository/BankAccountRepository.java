package com.example.s24278bank.repository;

import org.springframework.stereotype.Repository;
import com.example.s24278bank.model.BankAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BankAccountRepository {
    private List<BankAccount> bankAccountList = new ArrayList<>();

    public void create(BankAccount bankAccount) {
        bankAccountList.add(bankAccount);
    }

    public List<BankAccount> getAll() {
        return this.bankAccountList;
    }

    public Optional<BankAccount> findById(int id) {
        return bankAccountList.stream().filter(bankAccount -> id == bankAccount.getId()).findFirst();
    }

    public void removeAll() {
        bankAccountList = new ArrayList<>();
    }
}
