package com.example.projet_pfe_android.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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


    @Update
    void updateFournisseur(Fournisseur fournisseur);

    @Query("SELECT * FROM fournisseurs WHERE id=:fournisseurId")
    Fournisseur getFournisseurById(int fournisseurId);
}


