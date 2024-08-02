package com.github.bankingsystem.database.repository;

import com.github.bankingsystem.database.entity.BankAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
