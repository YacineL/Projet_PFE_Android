package com.example.projet_pfe_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projet_pfe_android.Model.Fournisseur;
import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Util.JavaUtil;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;

public class Add_fournisseur extends AppCompatActivity {

    private AppViewModel viewModel;
    private int fournisseurId;
    private Fournisseur fournisseur;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fournisseur);

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.getAllFournisseurs().observe(this, new Observer<List<Fournisseur>>() {
            @Override
            public void onChanged(List<Fournisseur> fournisseurs) {
                Toast.makeText(Add_fournisseur.this, "onChanged:"+Integer.toString(fournisseurs.size()), Toast.LENGTH_SHORT).show();
            }
        });

        EditText et_nom =(EditText)findViewById(R.id.et_nom);
        EditText et_prenom =(EditText)findViewById(R.id.et_prenom);
        EditText et_numero_tel=(EditText)findViewById(R.id.et_numero_tel);
        EditText et_adresse_mail=(EditText)findViewById(R.id.et_adresse_mail);
        EditText et_rue =(EditText)findViewById(R.id.et_rue);
        EditText et_ville =(EditText)findViewById(R.id.et_ville);
        EditText et_pays =(EditText)findViewById(R.id.et_pays);
        ImageView iv_picture=(ImageView)findViewById(R.id.iv_picture);
        Intent intent = getIntent();

        fournisseurId=intent.getIntExtra(JavaUtil.FOURNISSEUR_ID_KEY,JavaUtil.NO_RESULT);

        if (fournisseurId == JavaUtil.NO_RESULT) {
            setTitle("Ajouter fournisseur");
            invalidateOptionsMenu();
        } else {
            setTitle("Modifier fournisseur");
            fournisseur=viewModel.getFournisseurById(fournisseurId);
            et_nom.setText(fournisseur.getNom());
            et_prenom.setText(fournisseur.getPrenom());
            et_numero_tel.setText(fournisseur.getNumeroTel());
            et_adresse_mail.setText(fournisseur.getEmailFournisseur());
            et_rue.setText(fournisseur.getRue());
            et_ville.setText(fournisseur.getVille());
            et_pays.setText(fournisseur.getPays());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_fournisseur_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (fournisseurId == JavaUtil.NO_RESULT) {
            MenuItem menuItem = menu.findItem(R.id.delete2);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.valider2):
                saveFournisseur();
                finish();
                break;
            case (R.id.delete2) :
                break;
            case (android.R.id.home):
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFournisseur() {

        EditText et_nom =(EditText)findViewById(R.id.et_nom);
        EditText et_prenom =(EditText)findViewById(R.id.et_prenom);
        EditText et_numero_tel=(EditText)findViewById(R.id.et_numero_tel);
        EditText et_adresse_mail=(EditText)findViewById(R.id.et_adresse_mail);
        EditText et_rue =(EditText)findViewById(R.id.et_rue);
        EditText et_ville =(EditText)findViewById(R.id.et_ville);
        EditText et_pays =(EditText)findViewById(R.id.et_pays);
        ImageView iv_picture=(ImageView)findViewById(R.id.iv_picture);

        if (et_nom.getText()!=null && et_prenom.getText()!=null && et_numero_tel.getText() !=null && et_adresse_mail.getText() !=null){

            viewModel.insertFournisseur(new Fournisseur(et_nom.getText().toString(),
                    et_prenom.getText().toString(),
                    et_numero_tel.getText().toString(),
                   et_adresse_mail.getText().toString(),
                    et_rue.getText().toString(),
                    et_ville.getText().toString(),
                    et_pays.getText().toString() ));
        }
    }
}
