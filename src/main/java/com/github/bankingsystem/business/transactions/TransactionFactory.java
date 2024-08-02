package com.github.bankingsystem.business.transactions;

import com.github.bankingsystem.business.transactions.impl.DepositTransaction;
import com.github.bankingsystem.business.transactions.impl.TransferTransaction;
import com.github.bankingsystem.business.transactions.impl.WithdrawTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionFactory {

    private final ConcurrentHashMap<String, Transaction> transactions = new ConcurrentHashMap<>();

    @Autowired
    public TransactionFactory(DepositTransaction depositTransaction, WithdrawTransaction withdrawTransaction, TransferTransaction transferTransaction) {
        transactions.put(depositTransaction.getType(), depositTransaction);
        transactions.put(withdrawTransaction.getType(), withdrawTransaction);
        transactions.put(transferTransaction.getType(), transferTransaction);
    }

    public Transaction getTransaction(String transactionType) {
        return transactions.get(transactionType);
    }

}
