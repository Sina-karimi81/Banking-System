package com.github.bankingsystem.userInterface;

import com.github.bankingsystem.api.Account;
import com.github.bankingsystem.api.AccountCreationInputDTO;
import com.github.bankingsystem.business.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

import static com.github.bankingsystem.business.util.UserInput.*;

/**
 * This class is the interface that the used will interact with. this class implements {@link Runnable} so that it's methods
 * can be called asynchronously.
 */
@Service
public class AccountFunctions implements Runnable {

    public final Bank bank;

    @Autowired
    public AccountFunctions(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        System.out.println("Welcome to Banking System");
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\n");
            System.out.println(
                    """
                    Please choose an option:
                    1. create an account
                    2. get the balance of an account
                    3. deposit money to an account
                    4. withdraw money from an account
                    5. transfer money to an account
                    6. exit
                    """);

            choice = getUserInputAsInt(scanner);

            if (choice == 1) {
                System.out.println("Please enter your name:");
                String ownerName = getUserInputAsString(scanner);
                System.out.println("Please enter your an initial balance:");
                float initialBalance = getUserInputAsFloat(scanner);
                create(ownerName, initialBalance);
            } else if (choice == 2) {
                System.out.println("Please enter your account ID:");
                String accountId = getUserInputAsString(scanner);
                balance(accountId);
            } else if (choice == 3) {
                System.out.println("Please enter your account ID:");
                String accountId = getUserInputAsString(scanner);
                System.out.println("Please enter the amount you want to deposit:");
                float depositAmount = getUserInputAsFloat(scanner);
                deposit(accountId, depositAmount);
            } else if (choice == 4) {
                System.out.println("Please enter your account ID:");
                String accountId = getUserInputAsString(scanner);
                System.out.println("Please enter the amount you want to withdraw:");
                float withdrawAmount = getUserInputAsFloat(scanner);
                withdraw(accountId, withdrawAmount);
            } else if (choice == 5) {
                System.out.println("Please enter your account ID:");
                String sourceAccountId = getUserInputAsString(scanner);
                System.out.println("Please enter destination account ID:");
                String destinationAccountId = getUserInputAsString(scanner);
                System.out.println("Please enter the amount you want to transfer:");
                float transferAmount = getUserInputAsFloat(scanner);
                transfer(sourceAccountId, destinationAccountId, transferAmount);
            } else if (choice == 6) {
                System.out.println("Thanks for using our system!!!");
                return;
            } else {
                System.out.println("Command not recognized");
            }

        } while (true);
    }

    public void create(String ownerName,  Float initialBalance) {
        try {
            String accountId = bank.createAccount(new AccountCreationInputDTO(ownerName, initialBalance));
            System.out.println("generate accountId is: " + accountId + " For " + Thread.currentThread().getName());
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    public void balance(String accountId) {
        try {

            Account account = bank.getAccount(accountId);
            if (account == null) {
                System.out.println("No such account exists " + Thread.currentThread().getName());
            } else {
                System.out.println("account balance is " + account.getAmount() + " For " + Thread.currentThread().getName());
            }

        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    public void deposit(String accountId, Float amount) {
        try {

            if (bank.depositMoney(accountId, amount)) {
                System.out.println("Money deposited successfully " + Thread.currentThread().getName());
            } else {
                System.out.println("Money was not deposited " + Thread.currentThread().getName());
            }

        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    public void withdraw(String accountId, Float amount) {
        try {

            if (bank.withdrawMoney(accountId, amount)) {
                System.out.println("Money withdrawn successfully: " + Thread.currentThread().getName());
            } else {
                System.out.println("Money was not withdrawn " + Thread.currentThread().getName());
            }

        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    public void transfer(String sourceAccountId, String destinationAccountId, Float amount) {
        try {

            if (bank.transferMoney(sourceAccountId, destinationAccountId, amount)) {
                System.out.println("Money transferred successfully " + Thread.currentThread().getName());
            } else {
                System.out.println("Money was not transferred " + Thread.currentThread().getName());
            }

        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }
}
