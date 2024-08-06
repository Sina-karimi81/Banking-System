package com.github.bankingsystem.business.transactions.impl;

import com.github.bankingsystem.business.transactions.Transaction;
import com.github.bankingsystem.business.transactions.TransactionObserver;
import com.github.bankingsystem.database.entity.BankAccount;
import com.github.bankingsystem.database.repository.BankAccountRepository;
import com.github.bankingsystem.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TransferTransaction implements Transaction {

    private final TransactionObserver transactionObserver;
    private final BankAccountRepository bankAccountRepository;

    public TransferTransaction(TransactionObserver transactionObserver, BankAccountRepository bankAccountRepository) {
        this.transactionObserver = transactionObserver;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public String getType() {
        return TransactionType.TRANSFER.name();
    }

    @Override
    public boolean performTransaction(String accountId, Float amount) {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount should not be negative or null");
        }

        String[] split = accountId.split("-");
        String sourceAccountNumber = split[0];
        String destinationAccountNumber = split[1];

        try {
            Optional<BankAccount> sourceAccount = bankAccountRepository.findBankAccountById(sourceAccountNumber);
            Optional<BankAccount> destinationAccount = bankAccountRepository.findBankAccountById(destinationAccountNumber);

            if (sourceAccount.isPresent() && destinationAccount.isPresent()) {
                BankAccount sourceAcc = sourceAccount.get();
                BankAccount destinationAcc = destinationAccount.get();

                if (sourceAcc.getAmount() >= amount) {
                    sourceAcc.setAmount(sourceAcc.getAmount() - amount);
                    destinationAcc.setAmount(destinationAcc.getAmount() + amount);

                    bankAccountRepository.saveAll(List.of(sourceAcc, destinationAcc));
                    transactionObserver.onTransaction(sourceAccountNumber, destinationAccountNumber, getType(), amount);

                    return true;
                } else {
                    System.out.println("Not Enough Balance!!!! in account: " + accountId + " For " + Thread.currentThread().getName());
                    return false;
                }
            } else {
                System.out.println("one of the accounts provided does not exist"  + " For " + Thread.currentThread().getName());
                return false;
            }
        } catch (Exception e) {
            System.out.println("exception occurred while trying to transfer money from account " + sourceAccountNumber + " to account " + destinationAccountNumber + "\nexception: " + e.getMessage());
            return false;
        }
    }

}
