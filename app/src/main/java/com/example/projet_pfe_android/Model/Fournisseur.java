package com.example.projet_pfe_android.Model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "fournisseurs")
public class Fournisseur{

    public Fournisseur( String nom , String numeroTel, String emailFournisseur) {
        this.nom=nom;
        this.numeroTel=numeroTel;
        this.emailFournisseur=emailFournisseur;
    }

    public Fournisseur(String nom , String prenom , String numeroTel, String emailFournisseur
                          , String rue, String ville , String pays){
        this.nom=nom;
        this.prenom=prenom;
        this.numeroTel=numeroTel;
        this.emailFournisseur=emailFournisseur;
        this.rue=rue;
        this.ville=ville;
        this.pays=pays;
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
     public void setNom(String nom){
        this.nom=nom;
     }

    public String getPrenom(){
        return prenom;
    }

    public void setPrenom(String prenom){
        this.prenom=prenom;
    }

    public  String getNumeroTel(){
        return numeroTel;
    }
     public void setNumeroTel(String numeroTel){
        this.numeroTel=numeroTel;
     }


    public String getEmailFournisseur(){
        return emailFournisseur;
    }
    public void setEmailFournisseur(String emailFournisseur){
        this.emailFournisseur=emailFournisseur;
    }

    public String getRue(){
        return rue;
    }
    public void setRue(String rue){
        this.rue=rue;
    }

    public String getVille(){
        return ville;
    }
    public void setVille(String ville){
        this.ville=ville;
    }

    public String getPays(){
        return pays;
    }
    public void setPays( String pays){
        this.pays=pays;
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