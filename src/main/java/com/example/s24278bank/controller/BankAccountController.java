package com.example.s24278bank.controller;

import com.example.s24278bank.exception.NotFoundException;
import com.example.s24278bank.exception.ValidationException;
import com.example.s24278bank.model.BankAccount;
import com.example.s24278bank.model.TransactionResponse;
import com.example.s24278bank.model.TransactionStatus;
import com.example.s24278bank.service.BankAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @PostMapping("/registerAccount")
    public ResponseEntity<BankAccount> registerAccount(@RequestBody BankAccount bankAccount) {
        try {
            bankAccountService.create(bankAccount);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(bankAccountService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllAccounts")
    public List<BankAccount> getAll() {
        return bankAccountService.getAll();
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@PathVariable int id, @RequestBody int amount) {
        BankAccount tempBankAccount;
        TransactionStatus tempTransactionStatus;
        try {
            tempBankAccount = bankAccountService.getById(id);
            tempTransactionStatus = bankAccountService.withdraw(tempBankAccount, amount);
        } catch (NotFoundException e) {
            TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.DECLINED, 0);
            return ResponseEntity.ok(transactionResponse);
        }
        TransactionResponse transactionResponse = new TransactionResponse(tempTransactionStatus, tempBankAccount.getBalance());
        return ResponseEntity.ok(transactionResponse);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<TransactionResponse> deposit(@PathVariable int id, @RequestBody int amount) {
        BankAccount tempBankAccount;
        TransactionStatus tempTransactionStatus;
        try {
            tempBankAccount = bankAccountService.getById(id);
            tempTransactionStatus = bankAccountService.deposit(tempBankAccount, amount);
        } catch (NotFoundException e) {
            TransactionResponse transactionResponse = new TransactionResponse(TransactionStatus.DECLINED, 0);
            return ResponseEntity.ok(transactionResponse);
        }
        TransactionResponse transactionResponse = new TransactionResponse(tempTransactionStatus, tempBankAccount.getBalance());
        return ResponseEntity.ok(transactionResponse);
    }

}
