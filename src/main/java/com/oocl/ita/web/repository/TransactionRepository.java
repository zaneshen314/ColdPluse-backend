package com.oocl.ita.web.repository;

import com.oocl.ita.web.domain.po.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByUserId(Integer userId);
}
