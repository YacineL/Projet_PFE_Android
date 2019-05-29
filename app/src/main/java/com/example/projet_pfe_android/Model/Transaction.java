package com.example.projet_pfe_android.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {

    public static final int TYPE_VENTE = 0;
    public static final int TYPE_RECEPTION = 1;
    public static final int TYPE_AJUSTEMENT = 2;

    public Transaction() {
        //Required empty constructor
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int type; //Vente ou Réception

    @ColumnInfo(name = "total_amount")
    public double totalAmount;

    public long date;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public void set(int type, long date) {
        this.type = type;
        this.date = date;
    }
}
