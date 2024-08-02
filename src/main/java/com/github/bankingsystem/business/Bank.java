package com.github.bankingsystem.business;

import com.github.bankingsystem.api.Account;
import com.github.bankingsystem.api.AccountCreationInputDTO;
import com.github.bankingsystem.business.util.IdGenerator;
import com.github.bankingsystem.database.entity.BankAccount;
import com.github.bankingsystem.database.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Bank {
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public Bank(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public Long createAccount(AccountCreationInputDTO accountInfo) throws IllegalArgumentException {
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
            System.out.println("exception occurred while trying to save account to database");
            return null;
        }
    }

    public Account getAccount(Long accountId) throws IllegalArgumentException {
        if (accountId == null || accountId < 0) {
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
            System.out.println("exception occurred while trying to get account: " + accountId + " from database");
            return null;
        }
    }

    public boolean depositMoney(Long accountId, Float amount) throws IllegalArgumentException {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount should not be negative or null");
        }

        try {
            Optional<BankAccount> accountById = bankAccountRepository.findById(accountId);
            if (accountById.isPresent()) {
                BankAccount a = accountById.get();
                a.setAmount(a.getAmount() + amount);

                bankAccountRepository.save(a);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("exception occurred while trying to deposit money to account: " + accountId + " to database");
            return false;
        }
    }

    public boolean withdrawMoney(Long accountId, Float amount) throws IllegalArgumentException {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount should not be negative or null");
        }

        try {
            Optional<BankAccount> accountById = bankAccountRepository.findById(accountId);
            if (accountById.isPresent()) {
                BankAccount a = accountById.get();
                if (a.getAmount() >= amount) {
                    a.setAmount(a.getAmount() - amount);

                    bankAccountRepository.save(a);

                    return true;
                } else {
                    System.out.println("Not Enough Balance!!!! in account: " + accountId);
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("exception occurred while trying to withdraw money from account: " + accountId + " to database");
            return false;
        }
    }

    public boolean transferMoney(Long sourceAccount, Long destinationAccount, Float amount) {
        if (amount == null || amount < 0) {
            throw new IllegalArgumentException("Amount should not be negative or null");
        }

        boolean withdrawMoneyFromSource = withdrawMoney(sourceAccount, amount);
        boolean depositMoneyToDestination = depositMoney(destinationAccount, amount);

        return withdrawMoneyFromSource && depositMoneyToDestination;
    }

}
