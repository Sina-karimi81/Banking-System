package com.github.bankingsystem.business.transactions;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

@Component
public class TransactionLogger implements TransactionObserver {

    @Override
    public synchronized void onTransaction(String accountNumber, String transactionType, Float amount) throws IOException {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            bufferedWriter.write(String.format("For account with Id %s, at %s transaction with type %s was performed with amount %f in thread %s\n", accountNumber, new Date(), transactionType, amount, Thread.currentThread().getName()));
        }

    }

    @Override
    public synchronized void onTransaction(String sourceAccountNumber, String destinationAccountNumber, String transactionType, Float amount) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            bufferedWriter.write(String.format("From account with Id %s, at %s transaction with type %s was performed with amount %f to account %s in thread %s\n", sourceAccountNumber, new Date(), transactionType, amount, destinationAccountNumber, Thread.currentThread().getName()));
        }
    }

}
