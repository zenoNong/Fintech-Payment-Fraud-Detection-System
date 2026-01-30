package com.fintech.payment.repository;

import com.fintech.payment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// CRITICAL: You must specify <Transaction, Long> here!
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}