package com.example.projet_pfe_android.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projet_pfe_android.Model.Transaction;
import com.example.projet_pfe_android.Model.TransactionLine;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    long insertTransaction(Transaction transaction);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions")
    List<Transaction> getAllTransactions();

    @Query("DELETE FROM transactions")
    void deleteAllTransactions();

    @Insert
    void insertTransactionLine(TransactionLine transactionLine);

    @Query("SELECT * FROM transaction_lines")
    LiveData<List<TransactionLine>> getAllTransactionLines();

    @Query("SELECT * FROM transaction_lines WHERE type = 0")
    LiveData<List<TransactionLine>> getSalesTransactions();

    @Query("SELECT * FROM transaction_lines WHERE type = 1")
    LiveData<List<TransactionLine>> getReceivingTransactions();

    @Query("SELECT SUM((sales_price-price)*quantity) FROM transaction_lines")
    double getBenefice();


    @Query("SELECT SUM(sales_price*quantity) FROM transaction_lines")
    double getVentesBrutes();

//    @Query("SELECT ")
//    double getBenefice();
}
