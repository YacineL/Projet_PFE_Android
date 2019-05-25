package com.example.projet_pfe_android;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projet_pfe_android.Model.Fournisseur;
import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AppViewModel extends AndroidViewModel {
    //AppViewModel permet de gérer les changements de configuration : orientation, langue ...
    //Il n'est pas détruit tant que l'activité à laquelle il se rattache n'a pas été détruite
    //C'est aussi un maillon indispensable pour implémenter une MVVM : ModelViewViewModel architecture
    //ça simplifie les choses
    //Les Views ou l'interface graphique puiseront les données depuis le AppViewModel
    //Le AppViewModel se charge de ramener les données nécessaires depuis la BDD à travers Repository

    private Repository repository;
    private LiveData<List<Product>> products, transactionProducts;
    private LiveData<List<Fournisseur>> fournisseurs;
    private Transaction currentTransaction;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(getApplication());
        products = repository.getAllProducts();
        fournisseurs = repository.getAllFournisseurs();
        transactionProducts=repository.getCurrentTransactionProducts();
    }


    public LiveData<List<Product>> getAllProducts() {
        return products;
    }

    public LiveData<List<Fournisseur>> getAllFournisseurs() {
        return fournisseurs;
    }

    public void insertProduct(Product product) {
        repository.insertProduct(product);
    }

    public void insertFournisseur(Fournisseur fournisseur) {
        repository.insertFournisseur(fournisseur);
    }

    public void deleteAllProducts() {
        repository.deleteAllProducts();
    }

    public void deleteAllFournisseurs() {
        repository.deleteAllFournisseurs();
    }

    public void updateProduct(Product product) {
        repository.updateProduct(product);
    }

    public void updateFournisseur(Fournisseur fournisseur) {
        repository.updateFournisseur(fournisseur);
    }

    public void deleteProduct(Product product) {
        repository.deleteProduct(product);
    }

    public void deleteFournisseur(Fournisseur fournisseur) {
        repository.deleteFournisseur(fournisseur);
    }

    public LiveData<List<Product>> getCurrentTransactionProducts(){
        return transactionProducts;
    }

    public Transaction getCurrentTransaction(){
        if (currentTransaction==null)
            currentTransaction = new Transaction();

        return currentTransaction;
    }

    public void removeFromCurrentTransaction(Product product){
        product.setTransactionQty(0);
        updateProduct(product);
    }

    public void removeFromCurrentTransaction(List<Product> products){
        for (Product p : products){
            p.setTransactionQty(0);
        }
        updateProducts(products);
    }

    public void emptyCurrentTransaction(){
        removeFromCurrentTransaction(transactionProducts.getValue());
    }

    public void updateProducts(List<Product> products) {
        repository.updateProducts(products);
    }

    public double getStockValue(){
        double stockValue=0;
        try {
            stockValue = repository.getStockValue();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return stockValue;
    }
}
