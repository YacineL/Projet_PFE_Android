package com.example.projet_pfe_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projet_pfe_android.Model.Fournisseur;
import com.example.projet_pfe_android.Util.JavaUtil;
import com.example.projet_pfe_android.validator.FournisseurValidator;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

public class AddFournisseur extends AppCompatActivity {

    private AppViewModel viewModel;
    private int fournisseurId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fournisseur);
        ActionBar supportActionBar = getSupportActionBar();
        notNull(supportActionBar, "[AddFournisseur][onCreate] supportActionBar is null");
        supportActionBar.setDisplayShowHomeEnabled(true);

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.getAllFournisseurs().observe(this, new Observer<List<Fournisseur>>() {
            @Override
            public void onChanged(List<Fournisseur> fournisseurs) {
                Toast.makeText(AddFournisseur.this, "onChanged:" + fournisseurs.size(), Toast.LENGTH_SHORT).show();
            }
        });

        EditText et_nom = findViewById(R.id.et_nom);
        EditText et_prenom = findViewById(R.id.et_prenom);
        EditText et_numero_tel = findViewById(R.id.et_numero_tel);
        EditText et_adresse_mail = findViewById(R.id.et_adresse_mail);
        EditText et_rue = findViewById(R.id.et_rue);
        EditText et_ville = findViewById(R.id.et_ville);
        EditText et_pays = findViewById(R.id.et_pays);
        Intent intent = getIntent();

        fournisseurId = intent.getIntExtra(JavaUtil.FOURNISSEUR_ID_KEY, JavaUtil.NO_RESULT);

        if (fournisseurId == JavaUtil.NO_RESULT) {
            setTitle(getString(R.string.add_fournissuer_screen_title));
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.modify_fournisseur_screen_title));
            Fournisseur fournisseur = viewModel.getFournisseurById(fournisseurId);
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
            MenuItem menuItem = menu.findItem(R.id.delete_fournisseur);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.valider_fournisseur):
                saveFournisseur();
                break;
            case (R.id.delete_fournisseur):
                break;
            case (android.R.id.home):
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveFournisseur() {

        EditText et_nom = findViewById(R.id.et_nom);
        EditText et_prenom = findViewById(R.id.et_prenom);
        EditText et_numero_tel = findViewById(R.id.et_numero_tel);
        EditText et_adresse_mail = findViewById(R.id.et_adresse_mail);
        EditText et_rue = findViewById(R.id.et_rue);
        EditText et_ville = findViewById(R.id.et_ville);
        EditText et_pays = findViewById(R.id.et_pays);

        if (FournisseurValidator.validateInputForAdding(this)) {
            viewModel.insertFournisseur(
                    new Fournisseur(
                            et_nom.getText().toString(),
                            et_prenom.getText().toString(),
                            et_numero_tel.getText().toString(),
                            et_adresse_mail.getText().toString(),
                            et_rue.getText().toString(),
                            et_ville.getText().toString(),
                            et_pays.getText().toString()
                    )
            );
            finish();
        }

    }
}
