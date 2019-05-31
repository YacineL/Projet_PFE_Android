package com.example.projet_pfe_android.Data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projet_pfe_android.Model.Fournisseur;
import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Model.Transaction;
import com.example.projet_pfe_android.Model.TransactionLine;

//Ceci est la définition de la base de données, rien à changer dans ce fichier à part le version
//Il faut incrémenter le numéro de version à chaque ajout/modification de table (tt changement dans la BD)
@androidx.room.Database(entities = {Product.class, Fournisseur.class, Transaction.class, TransactionLine.class},version = 10) //(ici le numéro de version = 0)
public abstract class Database extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract FournisseurDao fournisseurDao();
    public abstract TransactionDao transactionDao();

    private static volatile Database instance;
    public static Database getInstance(Context context){
        synchronized (Database.class){
            if (instance == null){
                instance = Room.databaseBuilder(context, Database.class,"database")
                                .fallbackToDestructiveMigration()
                                .build();
            }
        }
        return instance;
    }

}
