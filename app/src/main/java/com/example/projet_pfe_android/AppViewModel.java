package com.example.projet_pfe_android;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.projet_pfe_android.Model.Product;
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

    public AppViewModel(@NonNull Application application) {
        super(application);
//        Must remain empty
    }

    public void init(){
//        These lines should be in the constructor but it doesn't work, I've been forced to leave
//        the constructor totally empty  (default) otherwise app crashes. Couldn't find a better fix (yet)
        repository = new Repository(getApplication());
        products = repository.getAllProducts();
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
}
