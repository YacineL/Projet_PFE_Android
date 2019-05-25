package com.example.projet_pfe_android.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_pfe_android.Model.Product;

import java.util.List;

//Ceci est le Data Access Object pour les produits
//Il faudra en créer un pour chaque table
//Dans ce fichier on définit les prototypes des méthodes accédant à la BD
//On utilise des annotations pour préciser le type d'opétation à faire sur la table Exp: @Query
@Dao
public interface ProductDao {

    @Query("SELECT * FROM products") //@Query vous permet d'executer une requete SQL, c'est l'annotation la plus générale
    LiveData<List<Product>> getAllProducts();

    @Insert //Annotation pour insérer seulement
    void insertProduct(Product product);

    @Delete //Annotation pour supprimer 1 seul enregistrement
    void deleteProduct(Product product);

    @Query("DELETE FROM products") //Exp: Cette requête supprime tout dans la table produits
    void deleteAllProducts();

    @Query("SELECT unit_price FROM products WHERE id=:productId")
    float getProductPriceById(int productId);

    @Update
    void updateProduct(Product product);


    @Query("SELECT * FROM products WHERE transaction_qty <> 0")
    LiveData<List<Product>> getCurrentTransactionProducts();

    @Update
    void updateProducts(List<Product> products);

//    @Query("UPDATE available_qty FROM products ,transactions  WHERE id.product=id.transaction and available_qty=:available_qty+transaction_qty")
    @Query("UPDATE products SET available_qty = available_qty +:transactionQty WHERE id=:productId")
    void commitTransactionQty(float transactionQty, int productId);

//    @Query("SELECT available_qty,unit_price, SUM(stock) FROM products WHERE available_qty*unit_price GROUP BY stock");
//    @Query("SELECT available_qty,unit_price, SUM(stock) FROM products WHERE available_qty*unit_price GROUP BY stock");

    @Query("SELECT SUM(available_qty * unit_price) FROM products")
    double getStockValue();

    @Query("SELECT * FROM products WHERE id=:productId")
    Product getProductById(int productId);
}
