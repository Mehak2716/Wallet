package com.swiggy.wallet.repository;

import com.swiggy.wallet.entities.Transaction;
import com.swiggy.wallet.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDAO extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t where t.sender = ?1 or t.receiver = ?1")
    public List<Transaction> findTransactionsOfUser(User user);
}
