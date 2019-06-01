package com.example.projet_pfe_android.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "transaction_lines")
public class TransactionLine {

    public TransactionLine() {
        //Required empty constructor
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "transaction_id")
    public int transactionId; //Id of the transaction to which this line belongs

    public int seq; //Sequence number of this line in its transaction 1,2,3, ...

    @ColumnInfo(name = "product_id")
    public int productId;

    @ColumnInfo(name = "picture_uri")
    public String pictureUri;

    public String productName;

    public double price;

    @ColumnInfo(name = "sales_price")
    public double salesPrice;

    public float quantity;

    public double amount;

    public long date;

    public int type;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public TransactionLine(int seq, int productId, String pictureUri, String productName, double price, double salesPrice, float quantity, double amount, int type) {
        this.seq = seq;
        this.productId = productId;
        this.pictureUri = pictureUri;
        this.productName = productName;
        this.price = price;
        this.salesPrice = salesPrice;
        this.quantity = quantity;
        this.amount = amount;
        this.type = type;
    }

    public static List<TransactionLine> toTransactionLine(List<Product> products, int transactionType) {
        List<TransactionLine> transactionLines = new ArrayList<>();
        int i = 0;
        for (Product p : products) {
            i++;
            double price = p.getUnitPrice(), salesPrice = p.getSalePrice();
            Log.d("PRICE", "SalesPrice: "+salesPrice+" | UnitPrice: "+price);
            double amount=0;
            switch (transactionType) {
                case Transaction.TYPE_VENTE:
                amount = p.getTransactionQty() * salesPrice;
                    break;
                case Transaction.TYPE_RECEPTION:
                amount = p.getTransactionQty() * price;
                    break;
            }
            transactionLines.add(new TransactionLine(
                    i,
                    p.getId(),
                    p.getPictureURI(),
                    p.getName(),
                    price,
                    salesPrice,
                    p.getTransactionQty(),
                    amount,
                    transactionType
            ));
        }
        return transactionLines;
    }

    public void reverseQty() {
        quantity = -quantity;
    }

    @NonNull
    @Override
    public String toString() {
        return "TnxId: " + transactionId + " | Seq: " + seq + " | ProdctId: " + productId + " | Quantity: " + quantity + " | Price: " + price + " | Amount: " + amount;
    }
}
