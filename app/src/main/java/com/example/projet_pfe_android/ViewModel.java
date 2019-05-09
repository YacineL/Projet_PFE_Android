package com.example.projet_pfe_android;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.projet_pfe_android.Data.Database;
import com.example.projet_pfe_android.Data.ProductDao;

public class ViewModel extends AndroidViewModel {
    //ViewModel permet de gérer les changements de configuration : orientation, langue ...
    //Il n'est pas détruit tant que l'activité à laquelle il se rattache n'a pas été détruite
    //C'est aussi un maillon indispensable pour implémenter une MVVM : ModelViewViewModel architecture
    //ça simplifie les choses
    //Les Views ou l'interface graphique puiseront les données depuis le ViewModel
    //Le ViewModel se charge de ramener les données nécessaires depuis la BDD

    private Database database;
    private ProductDao productDao;

    public ViewModel(@NonNull Application application) {
        super(application);

        //Connexion à la BDD
        database = Database.getInstance(application); //instance de la BDD
        productDao = database.productDao();
    }
}
