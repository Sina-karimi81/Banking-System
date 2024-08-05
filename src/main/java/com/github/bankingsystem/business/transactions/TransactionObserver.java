package com.github.bankingsystem.business.transactions;

import java.io.IOException;

public interface TransactionObserver {

    void onTransaction(String accountNumber, String transactionType, Float amount) throws IOException;

    void onTransaction(String sourceAccountNumber, String destinationAccountNumber, String transactionType, Float amount) throws IOException;

}
