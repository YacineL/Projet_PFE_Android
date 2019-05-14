package com.example.projet_pfe_android.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.projet_pfe_android.Model.Fournisseur;

import java.util.List;

@Dao
public interface FournisseurDao {


    @Query("SELECT * FROM fournisseurs")
    LiveData<List<Fournisseur>> getAllFournisseurs();

    @Insert
    void insertFournisseur(Fournisseur fournisseur);



    @Query("DELETE FROM fournisseurs")
    void deleteAllFournisseurs();

    @Delete
    void deleteFournisseur(Fournisseur fournisseur);

     }


