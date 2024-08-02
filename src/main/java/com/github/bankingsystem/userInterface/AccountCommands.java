package com.github.bankingsystem.userInterface;

import com.github.bankingsystem.api.Account;
import com.github.bankingsystem.api.AccountCreationInputDTO;
import com.github.bankingsystem.business.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AccountCommands {

    private final Bank bank;

    @Autowired
    public AccountCommands(Bank bank) {
        this.bank = bank;
    }

    @ShellMethod(key = "list", value = "lists all accounts")
    public void list() {
        bank.listAllAccounts();
    }

    @ShellMethod(key = "create", value = "creates an account for the user")
    public void create(@ShellOption(value = "-n", help = "account owner's name") String ownerName,
                       @ShellOption(value = "-b", help = "initial balance of the account")  Float initialBalance) {
        try {
            String accountId = bank.createAccount(new AccountCreationInputDTO(ownerName, initialBalance));
            System.out.println("accountId = " + accountId);
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    @ShellMethod(key = "balance", value = "shows an account's balance to the user")
    public void balance(@ShellOption(value = "-a", help = "user's account id") String accountId) {
        try {
            Account account = bank.getAccount(accountId);
            if (account == null) {
                System.out.println("No such account exists");
            } else {
                System.out.println("account balance is " + account.getAmount());
            }
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    @ShellMethod(key = "deposit", value = "deposit money to an account")
    public void deposit(@ShellOption(value = "-a", help = "user's account id") String accountId,
                        @ShellOption(value = "-m", help = "the amount to be deposited") Float amount) {
        try {
            if (bank.depositMoney(accountId, amount)) {
                System.out.println("Money deposited successfully");
            } else {
                System.out.println("Money was not deposited");
            }
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    @ShellMethod(key = "withdraw", value = "deposit money from an account")
    public void withdraw(@ShellOption(value = "-a", help = "user's account id") String accountId,
                         @ShellOption(value = "-m", help = "the amount to be withdrawn") Float amount) {
        try {
            if (bank.withdrawMoney(accountId, amount)) {
                System.out.println("Money withdrawn successfully");
            } else {
                System.out.println("Money was not withdrawn");
            }
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }

    @ShellMethod(key = "transfer", value = "transfer money from one account to another")
    public void transfer(@ShellOption(value = "-s", help = "source account where the money is going to be withdrawn") String sourceAccountId,
                         @ShellOption(value = "-d", help = "destination account where the money is going to be deposited") String destinationAccountId,
                         @ShellOption(value = "-m", help = "amount of money that is going to be transferred") Float amount) {
        try {
            if (bank.transferMoney(sourceAccountId, destinationAccountId, amount)) {
                System.out.println("Money transferred successfully");
            } else {
                System.out.println("Money was not transferred");
            }
        } catch (Exception e) {
            System.out.println("exception: " + e.getMessage());
        }
    }
}
