package com.github.bankingsystem.database.repository;

import com.github.bankingsystem.database.entity.BankAccount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This class is repository class that is going to be our interface with the database. I am using a Pessimistic locking mechanism
 * to lock a row in the database and prevent multiple threads from reading and writing to it at the same time
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select bankAcc from BankAccount bankAcc where bankAcc.id = :id")
    Optional<BankAccount> findBankAccountById(String id);

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select bankAcc from BankAccount bankAcc")
    List<BankAccount> findAll();

}
