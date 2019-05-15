package com.example.projet_pfe_android;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.projet_pfe_android.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class AppViewModel extends AndroidViewModel {
    //AppViewModel permet de gérer les changements de configuration : orientation, langue ...
    //Il n'est pas détruit tant que l'activité à laquelle il se rattache n'a pas été détruite
    //C'est aussi un maillon indispensable pour implémenter une MVVM : ModelViewViewModel architecture
    //ça simplifie les choses
    //Les Views ou l'interface graphique puiseront les données depuis le AppViewModel
    //Le AppViewModel se charge de ramener les données nécessaires depuis la BDD à travers Repository

    private Repository repository;
    private LiveData<List<Product>> products;
    private List<Product> currentTransaction=new ArrayList<>();

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(getApplication());
        products = repository.getAllProducts();
    }

    public void addToCurrentTransaction(Product product){
        currentTransaction.add(product);
    }

    public LiveData<List<Product>> getAllProducts(){
        return products;
    }

    public void insertProduct(Product product){
        repository.insertProduct(product);
    }

    public void deleteAllProducts(){
        repository.deleteAllProducts();
    }

    public void updateProduct(Product product) {
        repository.updateProduct(product);
    }
}
