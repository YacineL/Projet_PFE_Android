package com.example.projet_pfe_android.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "fournisseurs")
public class Fournisseur{

    @PrimaryKey
    public int id;

    public String nom;
    public String prenom;

    @ColumnInfo(name = "num_tel")
    public String numeroTel;

    @ColumnInfo(name = "email_four")
    public String emailFournisseur;

    public String rue;
    public String ville;
    public String pays;











}