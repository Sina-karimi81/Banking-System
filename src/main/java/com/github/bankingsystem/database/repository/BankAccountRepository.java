package com.github.bankingsystem.database.repository;

import com.github.bankingsystem.database.entity.BankAccount;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
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
