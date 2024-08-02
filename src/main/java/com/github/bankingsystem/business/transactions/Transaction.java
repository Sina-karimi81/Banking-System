package com.github.bankingsystem.business.transactions;

public interface Transaction {

    String getType();

    boolean performTransaction(String accountId, Float amount);

}
