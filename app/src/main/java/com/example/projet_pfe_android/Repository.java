package com.example.projet_pfe_android;

import android.app.Application;

import com.example.projet_pfe_android.Data.Database;
import com.example.projet_pfe_android.Data.ProductDao;

/***
 * Dans cette classe on va implémenter tous les accès à la base de donnée
 **/
public class Repository {

    private Database database;
    private ProductDao productDao;

    public Repository(Application application) {

        //Connexion à la BDD
        database = Database.getInstance(application); //instance de la BDD
        productDao = database.productDao();
    }
}
