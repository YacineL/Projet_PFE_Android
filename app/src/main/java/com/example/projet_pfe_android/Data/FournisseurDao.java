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


    @Insert
    void insertFournisseur(Fournisseur fournisseur);

    @Delete
    void deleteFournisseur(Fournisseur fournisseur);

    @Query("SELECT * FROM fournisseurs")
    List<Fournisseur> getAllFournisseurs();

    @Query("DELETE FROM fournisseurs")
    void deleteAllFournisseurs();
}
