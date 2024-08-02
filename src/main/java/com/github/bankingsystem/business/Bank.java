package com.github.bankingsystem.business;

import com.github.bankingsystem.api.Account;
import com.github.bankingsystem.api.AccountCreationInputDTO;
import com.github.bankingsystem.business.transactions.TransactionFactory;
import com.github.bankingsystem.business.util.IdGenerator;
import com.github.bankingsystem.business.util.TableGenerator;
import com.github.bankingsystem.database.entity.BankAccount;
import com.github.bankingsystem.database.repository.BankAccountRepository;
import com.github.bankingsystem.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.table.TableBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Bank {
    private final BankAccountRepository bankAccountRepository;
    private final TransactionFactory transactionFactory;

    @Autowired
    public Bank(BankAccountRepository bankAccountRepository, TransactionFactory transactionFactory) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionFactory = transactionFactory;
    }

    public void listAllAccounts() {
        try {
            List<BankAccount> all = bankAccountRepository.findAll();
            TableBuilder tableBuilder = TableGenerator.listToArrayTableModel(all);
            System.out.println(tableBuilder.build().render(120));
        } catch (Exception e) {
            System.out.println("exception occurred while trying to get all accounts from, database: \n" + e.getMessage());
        }
    }

    public String createAccount(AccountCreationInputDTO accountInfo) throws IllegalArgumentException {
        if (accountInfo.getOwnerName() == null || accountInfo.getOwnerName().isEmpty()) {
            throw new IllegalArgumentException("Owner's name should not be blank");
        }

        if (accountInfo.getAmount() == null ||accountInfo.getAmount() < 0) {
            throw new IllegalArgumentException("Amount should not be negative");
        }

        try {
            BankAccount account = BankAccount.builder()
                    .id(IdGenerator.generateAccountUniqueId())
                    .name(accountInfo.getOwnerName())
                    .amount(accountInfo.getAmount())
                    .build();

            BankAccount savedAccount = bankAccountRepository.save(account);

            return savedAccount.getId();
        } catch (Exception e) {
            System.out.println("exception occurred while trying to save account to database: " + e.getMessage());
            return null;
        }
    }

    public Account getAccount(String accountId) throws IllegalArgumentException {
        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("Account Id must be given in order to fetch the account data and must not be null");
        }

        try {
            Optional<BankAccount> accountById = bankAccountRepository.findById(accountId);

            if (accountById.isPresent()) {
                BankAccount a = accountById.get();
                return Account.builder()
                        .accountId(a.getId())
                        .ownerName(a.getName())
                        .amount(a.getAmount())
                        .build();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("exception occurred while trying to get account: " + accountId + " from database: " + e.getMessage());
            return null;
        }
    }

    public boolean depositMoney(String accountId, Float amount) throws IllegalArgumentException {
        return transactionFactory.getTransaction(TransactionType.DEPOSIT.name())
                .performTransaction(accountId, amount);
    }

    public boolean withdrawMoney(String accountId, Float amount) throws IllegalArgumentException {
        return transactionFactory.getTransaction(TransactionType.WITHDRAW.name())
                .performTransaction(accountId, amount);
    }

    public boolean transferMoney(String sourceAccount, String destinationAccount, Float amount) {
        return transactionFactory.getTransaction(TransactionType.TRANSFER.name())
                .performTransaction(sourceAccount + "-" + destinationAccount, amount);
    }

}
