package com.github.bankingsystem;

import com.github.bankingsystem.api.Account;
import com.github.bankingsystem.api.AccountCreationInputDTO;
import com.github.bankingsystem.business.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false",
        "spring.shell.script.enabled=false"
})
class BankingSystemApplicationTests {

    private final Bank bank;

    @Autowired
    BankingSystemApplicationTests(Bank bank) {
        this.bank = bank;
    }

    @Test
    void create_account_test() {
        AccountCreationInputDTO account = AccountCreationInputDTO.builder()
                .ownerName("Sina Karimi")
                .amount(1000f)
                .build();

        String generatedId = bank.createAccount(account);
        assertNotNull(generatedId, "an Id must be generated for the account");
        System.out.println("generatedId = " + generatedId);
    }

    @Test
    void create_account_with_invalid_input() {
        AccountCreationInputDTO accountBadName = AccountCreationInputDTO.builder()
                .ownerName("")
                .amount(1000f)
                .build();

        IllegalArgumentException IllegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountBadName), "expected createAccount to throw exception but didn't");
        assertEquals("Owner's name should not be blank", IllegalArgumentException.getMessage());

        AccountCreationInputDTO accountBadAmount = AccountCreationInputDTO.builder()
                .ownerName("Sina Karimi")
                .amount(-1000f)
                .build();

        IllegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bank.createAccount(accountBadAmount), "expected createAccount to throw exception but didn't");
        assertEquals("Amount should not be negative", IllegalArgumentException.getMessage());
    }

    @Test
    void create_multiple_account_id_uniqueness_test() {
        AccountCreationInputDTO account1 = AccountCreationInputDTO.builder()
                .ownerName("Sina Karimi")
                .amount(1000f)
                .build();

        String generatedId1 = bank.createAccount(account1);
        assertNotNull(generatedId1, "an Id must be generated for the account");
        System.out.println("generatedId = " + generatedId1);

        AccountCreationInputDTO account2 = AccountCreationInputDTO.builder()
                .ownerName("Sina Karimi")
                .amount(1000f)
                .build();

        String generatedId2 = bank.createAccount(account2);
        assertNotNull(generatedId2, "an Id must be generated for the account");
        System.out.println("generatedId = " + generatedId2);

        AccountCreationInputDTO account3 = AccountCreationInputDTO.builder()
                .ownerName("Sina Karimi")
                .amount(1000f)
                .build();

        String generatedId3 = bank.createAccount(account3);
        assertNotNull(generatedId3, "an Id must be generated for the account");
        System.out.println("generatedId = " + generatedId3);

        assertNotEquals(generatedId1, generatedId2, "generatedId1 and generatedId2 must not match");
        assertNotEquals(generatedId1, generatedId3, "generatedId1 and generatedId3 must not match");
        assertNotEquals(generatedId2, generatedId3, "generatedId2 and generatedId3 must not match");
    }

    @Test
    void get_account() {
        AccountCreationInputDTO inputAccountInfo = AccountCreationInputDTO.builder()
                .ownerName("Sina Karimi")
                .amount(2500f)
                .build();

        String accountId = bank.createAccount(inputAccountInfo);
        assertNotNull(accountId, "an Id must be generated for the account");
        System.out.println("accountId = " + accountId);

        Account account = bank.getAccount(accountId);
        System.out.println("account = " + account);
        assertNotNull(account, "received account from DB must not be null");
        assertEquals(accountId, account.getAccountId(), "generated Id when persisting must be the same when account is fetched");
        assertEquals(2500f, account.getAmount(), "amount must be 2500");
        assertEquals("Sina Karimi", account.getOwnerName(), "account owner's name must be Sina Karimi");
    }

    @Test
    void get_non_existed_account() {
        Account account = bank.getAccount("4");
        assertNull(account, "received account must be null since no account with given id: " + 1 + " doesn't exist");
    }

    @Test
    void get_account_illegal_input() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bank.getAccount(null), "expected getAccount to throw exception but didn't");
        assertEquals("Account Id must be given in order to fetch the account data and must not be null", illegalArgumentException.getMessage());
    }

    @Test
    void deposit_money() {
        String accountId = "1";

        boolean succeeded = bank.depositMoney(accountId, 155.5f);
        assertTrue(succeeded, "depositing money must be successful");

        Account account = bank.getAccount(accountId);
        System.out.println("account = " + account);
        assertEquals(accountId, account.getAccountId(), "generated Id when persisting must be the same when account is fetched");
        assertEquals(5155.5f, account.getAmount(), "amount must be 5155.5");
        assertEquals("Sina Karimi", account.getOwnerName(), "account owner's name must be Sina Karimi");
    }

    @Test
    void deposit_money_negative_input() {
        String accountId = "1";
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bank.depositMoney(accountId, -154.75f), "expected deposit money to throw exception but didn't");
        assertEquals("Amount should not be negative or null", illegalArgumentException.getMessage());
    }

    @Test
    void deposit_money_non_existed_account() {
        boolean succeeded = bank.depositMoney("3", 154.75f);
        assertFalse(succeeded, "deposit should fail for a non existing account");
    }

    @Test
    void withdraw_money() {
        String accountId = "1";

        boolean succeeded = bank.withdrawMoney(accountId, 155.5f);
        assertTrue(succeeded, "depositing money must be successful");

        Account account = bank.getAccount(accountId);
        System.out.println("account = " + account);
        assertEquals(accountId, account.getAccountId(), "generated Id when persisting must be the same when account is fetched");
        assertEquals(4844.5f, account.getAmount(), "amount must be 4844.5");
        assertEquals("Sina Karimi", account.getOwnerName(), "account owner's name must be Sina Karimi");
    }

    @Test
    void withdraw_money_negative_input() {
        String accountId = "1";
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bank.withdrawMoney(accountId, -154.75f), "expected withdraw money to throw exception but didn't");
        assertEquals("Amount should not be negative or null", illegalArgumentException.getMessage());
    }

    @Test
    void withdraw_money_not_enough_balance() {
        String accountId = "1";
        boolean withdrawMoney = bank.withdrawMoney(accountId, 6000.0f);
        assertFalse(withdrawMoney, "withdraw money should fail but didn't");
    }

    @Test
    void withdraw_money_non_existed_account() {
        boolean succeeded = bank.withdrawMoney("3", 154.75f);
        assertFalse(succeeded, "deposit should fail for a non existing account");
    }

    @Test
    void transfer_money() {
        var sourceAccount = "1";
        var destinationAccount = "2";

        boolean succeeded = bank.transferMoney(sourceAccount, destinationAccount, 250.0f);
        assertTrue(succeeded, "transferring money must be successful");

        Account account1 = bank.getAccount(sourceAccount);
        System.out.println("account = " + account1);
        assertEquals(sourceAccount, account1.getAccountId(), "generated Id when persisting must be the same when account is fetched");
        assertEquals(4750.0f, account1.getAmount(), "amount must be 4750.0");

        Account account2 = bank.getAccount(destinationAccount);
        System.out.println("account = " + account2);
        assertEquals(destinationAccount, account2.getAccountId(), "generated Id when persisting must be the same when account is fetched");
        assertEquals(4250.0f, account2.getAmount(), "amount must be 4250.0");
    }

    @Test
    void transfer_money_non_existed_account() {
        var sourceAccount = "1";
        var destinationAccount = "3";
        boolean succeeded = bank.transferMoney(sourceAccount, destinationAccount, 154.75f);
        assertFalse(succeeded, "transfer should fail for a non existing account");
    }

    @Test
    void transfer_money_negative_input() {
        var sourceAccount = "1";
        var destinationAccount = "2";
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bank.transferMoney(sourceAccount, destinationAccount, -154.75f), "expected getAccount to throw exception but didn't");
        assertEquals("Amount should not be negative or null", illegalArgumentException.getMessage());
    }

    @Test
    void transfer_money_not_enough_balance() {
        var sourceAccount = "1";
        var destinationAccount = "2";
        boolean succeeded = bank.transferMoney(sourceAccount, destinationAccount, 6000.0f);
        assertFalse(succeeded, "transfer should fail for when source account doesn't have enough money");
    }

    //todo: implement multi threading tests
}
