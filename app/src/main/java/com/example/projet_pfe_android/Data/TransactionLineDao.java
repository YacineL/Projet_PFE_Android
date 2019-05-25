package com.example.projet_pfe_android.Data;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TransactionLineDao {



// MES NVLE REQUETES DE TRANSACTIONLINES

  @Query("SELECT SUM(transaction_qty * sale_price) FROM products")
    double getVenteBrute();

   @Query("SELECT SUM(transaction_qty * (sale_price - unit_price))FROM products")
    double getBenefice();
}
