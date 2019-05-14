package com.example.projet_pfe_android.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_lines")
public class TransactionLine {

    public TransactionLine() {
        //Required empty constructor
    }

    @ColumnInfo(name = "transaction_id")
    public int transactionId; //Id of the transaction to which this line belongs

    public int seq; //Sequence number of this line in its transaction 1,2,3, ...

    @ColumnInfo(name = "product_id")
    public int productId;

    public float quantity;

    public float amount;

}
