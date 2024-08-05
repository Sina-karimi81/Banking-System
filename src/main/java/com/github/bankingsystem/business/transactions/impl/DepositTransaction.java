package com.github.bankingsystem.business.transactions.impl;

import com.github.bankingsystem.business.transactions.Transaction;
import com.github.bankingsystem.business.transactions.TransactionObserver;
import com.github.bankingsystem.database.entity.BankAccount;
import com.github.bankingsystem.database.repository.BankAccountRepository;
import com.github.bankingsystem.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DepositTransaction implements Transaction {

    private final TransactionObserver transactionObserver;
    private final BankAccountRepository bankAccountRepository;

    public DepositTransaction(TransactionObserver transactionObserver, BankAccountRepository bankAccountRepository) {
        this.transactionObserver = transactionObserver;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public String getType() {
        return TransactionType.DEPOSIT.name();
    }

    @Override
    public boolean performTransaction(String accountId, Float amount) {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount should not be negative or null");
        }

        try {
            Optional<BankAccount> accountById = bankAccountRepository.findBankAccountById(accountId);
            if (accountById.isPresent()) {
                BankAccount a = accountById.get();
                a.setAmount(a.getAmount() + amount);

                bankAccountRepository.save(a);
                transactionObserver.onTransaction(accountId, getType(), amount);

                return true;
            } else {
                System.out.println("No such account exists: " + accountId);
                return false;
            }
        } catch (Exception e) {
            System.out.println("exception occurred while trying to deposit money to account: " + accountId + " to database: " + e.getMessage());
            return false;
        }
    }
}
