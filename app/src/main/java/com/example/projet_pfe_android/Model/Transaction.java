package com.example.projet_pfe_android.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {

    public static final int TYPE_VENTE = 0;
    public static final int TYPE_RECEPTION = 1;

    public Transaction() {
        //Required empty constructor
    }

    @PrimaryKey(autoGenerate = true)
    int id;

    int type; //Vente ou Réception

    @ColumnInfo(name = "number_of_products")
    int numberOfProducts;

    @ColumnInfo(name = "total_amount")
    double totalAmount;

    long date;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
