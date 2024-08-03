package com.github.bankingsystem.business.transactions;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class TransactionLogger implements TransactionObserver {

    @Override
    public synchronized void onTransaction(String accountNumber, String transactionType, Float amount) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            bufferedWriter.write(String.format("For account with Id %s , transaction with type %s was performed with amount %f\n", accountNumber,transactionType, amount));
        } catch (IOException e) {
            System.out.println("exception occurred while trying to save transaction log: " + e.getMessage());
        }

    }

    @Override
    public synchronized void onTransaction(String sourceAccountNumber, String destinationAccountNumber, String transactionType, Float amount) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            bufferedWriter.write(String.format("From account with Id %s , transaction with type %s was performed with amount %f to account %s\n", sourceAccountNumber,transactionType, amount, destinationAccountNumber));
        } catch (IOException e) {
            System.out.println("exception occurred while trying to save transaction log: " + e.getMessage());
        }
    }

}
