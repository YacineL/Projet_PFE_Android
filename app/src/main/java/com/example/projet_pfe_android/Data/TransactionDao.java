package com.example.projet_pfe_android.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projet_pfe_android.Model.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insertTransaction(Transaction transaction);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions")
    List<Transaction> getAllTransactions();

    @Query("DELETE FROM transactions")
    void deleteAllTransactions();

}
