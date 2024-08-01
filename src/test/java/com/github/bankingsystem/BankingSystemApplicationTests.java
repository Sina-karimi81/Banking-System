package com.github.bankingsystem;

import com.github.bankingsystem.api.Account;
import com.github.bankingsystem.api.AccountCreationInputDTO;
import com.github.bankingsystem.business.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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

        Long generatedId = bank.createAccount(account);
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

        Long generatedId1 = bank.createAccount(account1);
        assertNotNull(generatedId1, "an Id must be generated for the account");
        System.out.println("generatedId = " + generatedId1);

        AccountCreationInputDTO account2 = AccountCreationInputDTO.builder()
                .ownerName("Sina Karimi")
                .amount(1000f)
                .build();

        Long generatedId2 = bank.createAccount(account2);
        assertNotNull(generatedId2, "an Id must be generated for the account");
        System.out.println("generatedId = " + generatedId2);

        AccountCreationInputDTO account3 = AccountCreationInputDTO.builder()
                .ownerName("Sina Karimi")
                .amount(1000f)
                .build();

        Long generatedId3 = bank.createAccount(account3);
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

        Long accountId = bank.createAccount(inputAccountInfo);
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
        Account account = bank.getAccount(1L);
        assertNull(account, "received account must be null since no account with given id: " + 1 + " doesn't exist");
    }

    @Test
    void get_account_illegal_input() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bank.getAccount(null), "expected getAccount to throw exception but didn't");
        assertEquals("Account Id must be given in order to fetch the account data and must not be null", illegalArgumentException.getMessage());
    }

    @Test
    void deposit_money() {
        Long accountId = 5323523150815117423L;

        boolean succeeded = bank.depositMoney(accountId, 154.75f);
        assertTrue(succeeded, "depositing money must be successful");

        Account account = bank.getAccount(accountId);
        System.out.println("account = " + account);
        assertEquals(accountId, account.getAccountId(), "generated Id when persisting must be the same when account is fetched");
        assertEquals(2654.75f, account.getAmount(), "amount must be 2500");
        assertEquals("Sina Karimi", account.getOwnerName(), "account owner's name must be Sina Karimi");
    }

    @Test
    void deposit_money_negative_input() {
        Long accountId = 8567578311883312306L;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bank.depositMoney(accountId, -154.75f), "expected getAccount to throw exception but didn't");
        assertEquals("Amount should not be negative or null", illegalArgumentException.getMessage());
    }

    @Test
    void deposit_money_non_existed_account() {
        boolean succeeded = bank.depositMoney(1L, 154.75f);
        assertFalse(succeeded, "deposit should fail for a non existing account");
    }

    @Test
    void withdraw_money() {
        Long accountId = 5323523150815117423L;

        boolean succeeded = bank.withdrawMoney(accountId, 154.75f);
        assertTrue(succeeded, "depositing money must be successful");

        Account account = bank.getAccount(accountId);
        System.out.println("account = " + account);
        assertEquals(accountId, account.getAccountId(), "generated Id when persisting must be the same when account is fetched");
        assertEquals(2500.0f, account.getAmount(), "amount must be 2500");
        assertEquals("Sina Karimi", account.getOwnerName(), "account owner's name must be Sina Karimi");
    }

    @Test
    void withdraw_money_negative_input() {
        Long accountId = 8567578311883312306L;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> bank.withdrawMoney(accountId, -154.75f), "expected getAccount to throw exception but didn't");
        assertEquals("Amount should not be negative or null", illegalArgumentException.getMessage());
    }

    @Test
    void withdraw_money_non_existed_account() {
        boolean succeeded = bank.withdrawMoney(1L, 154.75f);
        assertFalse(succeeded, "deposit should fail for a non existing account");
    }

}
