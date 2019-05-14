package com.example.projet_pfe_android.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {

    public Transaction() {
        //Required empty constructor
    }

    @PrimaryKey
    int id;

    String type; //Vente ou Réception

    @ColumnInfo(name = "number_of_products")
    int numberOfProducts;

    @ColumnInfo(name = "total_amount")
    float totalAmount;

    long date;
}
