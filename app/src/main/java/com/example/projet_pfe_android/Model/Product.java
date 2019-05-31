package com.example.projet_pfe_android.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static java.lang.Math.abs;

//Ceci est un exemple de class définissant une table Room, à utiliser comme modèle pour créer les autres tables
@Entity(tableName = "products")
public class Product {

    //J'ai mis des champs qui me parraissent importants, à vous de modifier selon votre diagramme de classes


    public Product() {
        //Required empty constructor
    }

    @Ignore
    public Product(String name, int availableQty) {
        this.name = name;
        this.availableQty = availableQty;
        unitPrice=2;
    }

    @Ignore
    public Product(String name,String brand,String description,
                   float safetyStockQty,String uom,double unitPrice,
                   double salePrice,String pictureURI,String serialNumber){
        this.name=name;
        this.brand=brand;
        this.description=description;
        this.safetyStockQty=safetyStockQty;
        this.UOM=uom;
        this.unitPrice=unitPrice;
        this.salePrice=salePrice;
        this.serialNumber = serialNumber;
        this.pictureURI=pictureURI;
    }

    @PrimaryKey(autoGenerate = true) //Définit id en tant que clé primaire
    public int id;

    public String name="";
    public String brand="";
    public String description="";

    @ColumnInfo(name = "available_qty") //Cette ligne impose le nom du champs correspondant dans la BD
    public float availableQty=0;

    @ColumnInfo(name = "safety_stock_qty")
    public float safetyStockQty=0;

    @ColumnInfo(name = "mip")
    public float MIP=0; //Maximum Inventory Position, le max permettant de calculer la quantité à réapprovisionner

    @ColumnInfo(name = "uom")
    public String UOM=""; //Unit Of Measurement

    @ColumnInfo(name = "serial_number")
    public String serialNumber =""; //Barcode

    @ColumnInfo(name = "unit_price")
    public double unitPrice=0; //Price for 1 UOM

    @ColumnInfo(name = "sale_price")
    public double salePrice=0; //Price for 1 UOM

    @ColumnInfo(name = "picture_uri")
    public String pictureURI="";

    @ColumnInfo(name = "transaction_qty")
    public float transactionQty=0;

    public boolean active;

//    METHODS

    public boolean equals(Product otherProduct){
        return name.equals(otherProduct.getName()) &&
                availableQty == otherProduct.getAvailableQty() &&
                transactionQty == otherProduct.getTransactionQty() &&
                pictureURI.equals(otherProduct.getPictureURI());
    }

//    GETTERS AND SETTERS


    public String getDescription() { return description; }

    public double getSalePrice() { return salePrice; }

    public String getSerialNumber() { return serialNumber; }

    public void setDescription(String description) { this.description = description; }

    public void setSalePrice(double salePrice) { this.salePrice = salePrice; }

    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

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

    public float getTransactionQty() { return transactionQty; }

    public void setTransactionQty(float transactionQty) { this.transactionQty = abs(transactionQty); }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
