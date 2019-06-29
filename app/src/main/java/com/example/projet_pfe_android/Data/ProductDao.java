package com.example.projet_pfe_android.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Model.TransactionLine;

import java.util.List;


@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();

    @Insert //insérer seulement
    void insertProduct(Product product);

    @Delete //supprimer 1 seul enregistrement
    void deleteProduct(Product product);

    @Query("DELETE FROM products") //supprime tout dans la table produits
    void deleteAllProducts();

    @Query("SELECT unit_price FROM products WHERE id=:productId")
    float getProductPriceById(int productId);

    @Update
    void updateProduct(Product product);


    @Query("SELECT * FROM products WHERE transaction_qty <> 0")
    LiveData<List<Product>> getCurrentTransactionProducts();

    @Update
    void updateProducts(List<Product> products);

    @Query("UPDATE products SET available_qty = available_qty+:transactionQty WHERE id=:productId")
    void commitTransactionQty(float transactionQty, int productId);

    @Query("SELECT SUM(available_qty * unit_price) FROM products")
    double getStockValue();

    @Query("SELECT * FROM products WHERE id=:productId")
    Product getProductById(int productId);

    @Query("SELECT * FROM products WHERE available_qty > 0 ")
    LiveData<List<Product>> getAvailableProducts();


    // requetes pr mettre les produits en sctock sous securite et repture stock

    @Query("SELECT * FROM products WHERE available_qty<=safety_stock_qty")
    LiveData<List<Product>> getProductStockSecurite();

    @Query("SELECT * FROM products WHERE available_qty=0")
    LiveData<List<Product>> getProductReptureStock();
    //--------------------------------------------------------------------------------

}
