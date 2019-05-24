package com.example.projet_pfe_android.Data;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TransactionLineDao {



    @Query("SELECT TRANSACTIONS.transaction_qty, products.salePrice, SUM(vente_brute) FROM TRANSACTIONS WHERE transaction_qty*salePrice GROUP BY vente_brute");

    @Query("SELECT TRANSACTIONS.transaction_qty, products.salePrice,products.unit_price, SUM(benefices) FROM TRANSACTIONS WHERE transaction_qty*(salePrice-unit_price) GROUP BY benefice ")


}
