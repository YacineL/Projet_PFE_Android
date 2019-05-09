package com.example.projet_pfe_android.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Ceci est un exemple de class définissant une table Room, à utiliser comme modèle pour créer les autres tables
@Entity(tableName = "products")
public class Product {

    //J'ai mis des champs qui me parraissent importants, à vous de modifier selon votre diagramme de classes

    @PrimaryKey //Définit id en tant que clé primaire
    public int id;

    public String name;
    public String brand;

    @ColumnInfo(name = "available_qty") //Cette ligne impose le nom du champs correspondant dans la BD
    public int availableQty;

    @ColumnInfo(name = "safety_stock_qty")
    public int safetyStockQty;

    @ColumnInfo(name = "mip")
    public int MIP; //Maximum Inventory Position, le max permettant de calculer la quantité à réapprovisionner

    @ColumnInfo(name = "uom")
    public String UOM; //Unit Of Measurement

    @ColumnInfo(name = "unit_price")
    public double unitPrice; //Price for 1 UOM

    public String pictureURI;
}
