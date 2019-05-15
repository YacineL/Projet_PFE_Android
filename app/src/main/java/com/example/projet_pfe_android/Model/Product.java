package com.example.projet_pfe_android.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//Ceci est un exemple de class définissant une table Room, à utiliser comme modèle pour créer les autres tables
@Entity(tableName = "products")
public class Product {

    //J'ai mis des champs qui me parraissent importants, à vous de modifier selon votre diagramme de classes


    public Product() {
        //Required empty constructor
    }

    public Product(String name, int availableQty) {
        this.name = name;
        this.availableQty = availableQty;
    }

    @PrimaryKey(autoGenerate = true) //Définit id en tant que clé primaire
    public int id;

    public String name;
    public String brand;

    @ColumnInfo(name = "available_qty") //Cette ligne impose le nom du champs correspondant dans la BD
    public float availableQty;

    @ColumnInfo(name = "safety_stock_qty")
    public float safetyStockQty;

    @ColumnInfo(name = "mip")
    public float MIP; //Maximum Inventory Position, le max permettant de calculer la quantité à réapprovisionner

    @ColumnInfo(name = "uom")
    public String UOM; //Unit Of Measurement

    @ColumnInfo(name = "unit_price")
    public double unitPrice; //Price for 1 UOM

    @ColumnInfo(name = "picture_uri")
    public String pictureURI;

    @Ignore //Cette ligne impose le nom du champs correspondant dans la BD
    public float transactionQty;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(float availableQty) {
        this.availableQty = availableQty;
    }

    public float getSafetyStockQty() {
        return safetyStockQty;
    }

    public void setSafetyStockQty(float safetyStockQty) {
        this.safetyStockQty = safetyStockQty;
    }

    public float getMIP() {
        return MIP;
    }

    public void setMIP(float MIP) {
        this.MIP = MIP;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPictureURI() {
        return pictureURI;
    }

    public void setPictureURI(String pictureURI) {
        this.pictureURI = pictureURI;
    }

    public float getTransactionQty() {
        return transactionQty;
    }

    public void setTransactionQty(float transactionQty) {
        this.transactionQty = transactionQty;
    }
}
