package com.example.projet_pfe_android.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "fournisseurs")
public class Fournisseur{

    public Fournisseur() {
        //Required empty constructor
    }

    //Fields must be public
    @PrimaryKey(autoGenerate = true)
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

    public String getNom() {

        return nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public  String getNumeroTel(){
        return numeroTel;
    }

    public String getEmailFournisseur(){
        return emailFournisseur;
    }

    public String getRue(){
        return rue;
    }

    public String getVille(){
        return ville;
    }

    public String getPays(){
        return pays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Fournisseur autreFournisseur){
        return this.nom.equals(autreFournisseur.getNom()) &&
                this.prenom.equals(autreFournisseur.getPrenom()) &&
                this.numeroTel.equals(autreFournisseur.getNumeroTel()) &&
                this.emailFournisseur.equals(autreFournisseur.getEmailFournisseur());
    }
}