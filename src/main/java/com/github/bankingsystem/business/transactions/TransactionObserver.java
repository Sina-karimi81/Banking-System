package com.github.bankingsystem.business.transactions;

public interface TransactionObserver {

    void onTransaction(String accountNumber, String transactionType, Float amount);

    void onTransaction(String sourceAccountNumber, String destinationAccountNumber, String transactionType, Float amount);

}
