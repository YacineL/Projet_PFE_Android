package com.example.projet_pfe_android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.projet_pfe_android.Model.Transaction;
import com.example.projet_pfe_android.Util.JavaUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppViewModel viewModel;
    private ConstraintLayout clValidationWindow;
    private FrameLayout flValidationWindow;
    private TextView tvProductName;
    private AppCompatEditText etQuantity;
    private Button bValider, bAnnuler;


    // SS = en dessous du stock sécurité ZS = En rupture (Zero Stock)
    private TextView tvCaisse, tvBenefice, tvValeurStock, tvVentesBrutes, tvProduitsSS, tvProduitsZS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        tvValeurStock.setText(JavaUtil.currencyString(viewModel.getStockValue()));
        tvBenefice.setText(JavaUtil.currencyString(viewModel.getBenefice(/*Parametre a ajouter : date1 date2*/)));
        tvVentesBrutes.setText(JavaUtil.currencyString(viewModel.getVentesBrutes(/*Parametre a ajouter : date1 date2*/)));
        Toast.makeText(this, "Stock Value = " + JavaUtil.currencyString(viewModel.getStockValue()), Toast.LENGTH_SHORT).show();
        setupValidationWindow();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CurrentTransactionActivity.class);
                intent.putExtra(JavaUtil.TRANSACTION_TYPE_KEY, Transaction.TYPE_VENTE);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        tvCaisse = findViewById(R.id.tv_caisse);
        tvBenefice = findViewById(R.id.tv_benefice);
        tvVentesBrutes = findViewById(R.id.tv_ventes_brutes);
        tvValeurStock = findViewById(R.id.tv_valeur_stock);
        tvProduitsSS = findViewById(R.id.tv_label_produits_ss);
        tvProduitsZS = findViewById(R.id.tv_label_produits_sz);
        tvProduitsSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListProduit.class);
                intent.putExtra(JavaUtil.PRDUCTS_STOCK_VALUE_TYPE, "securité");
                startActivity(intent);
            }
        });

        tvProduitsZS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListProduit.class);
                intent.putExtra(JavaUtil.PRDUCTS_STOCK_VALUE_TYPE, "rupture");
                startActivity(intent);
            }
        });

        CardView cvCaisse = findViewById(R.id.caisse);
        cvCaisse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showValidationWindow();
            }
        });

        float caisseValue = JavaUtil.getCaisse(this);
        tvCaisse.setText(String.valueOf(JavaUtil.getCaisse(this)));
        Toast.makeText(this, "NC = "+caisseValue, Toast.LENGTH_SHORT).show();
        if (caisseValue<0)
            tvCaisse.setTextColor(Color.RED);
        else
            tvCaisse.setTextColor(Color.BLACK);

        tvCaisse.setText(JavaUtil.currencyString((double)caisseValue));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_produits) {
            // Handle the camera action
            Intent intent = new Intent(this, ListProduit.class);
            startActivity(intent);
        } else if (id == R.id.nav_fournisseurs) {
            Intent intent = new Intent(this, ListFournisseur.class);
            startActivity(intent);
        } else if (id == R.id.nav_ventes) {
            Intent intent = new Intent(this, TransactionHistoryActivity.class);
            intent.putExtra(JavaUtil.TRANSACTION_TYPE_KEY, Transaction.TYPE_VENTE);
            startActivity(intent);
        } else if (id == R.id.nav_receptions) {
            Intent intent = new Intent(this, TransactionHistoryActivity.class);
            intent.putExtra(JavaUtil.TRANSACTION_TYPE_KEY, Transaction.TYPE_RECEPTION);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setupValidationWindow() {
        clValidationWindow = findViewById(R.id.cl_validation_window2);
        flValidationWindow = findViewById(R.id.fl_validation_window2);
        tvProductName = findViewById(R.id.tv_nom_produit2);
        etQuantity = findViewById(R.id.et_quantity2);
        bValider = findViewById(R.id.b_valider2);
        bAnnuler = findViewById(R.id.b_annuler2);
        bValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add product with qty to transaction list
                try {
                    float qty = Float.parseFloat(etQuantity.getText().toString());
                    JavaUtil.saveCaisse(MainActivity.this,qty);
                    tvCaisse.setText(String.valueOf(JavaUtil.getCaisse(MainActivity.this)));
                    // Toast.makeText(ListProduit.this, "Transaction mise à jour.", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Log.d("#EXP", "ListProduit.bValider.onClick: " + e.getMessage());
                }
                hideValidationWindow();

            }
        });
        bAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideValidationWindow();
            }
        });
    }

    private void hideValidationWindow() {
        clValidationWindow.setVisibility(View.GONE);
        flValidationWindow.setVisibility(View.GONE);
    }

    private void showValidationWindow() {
        clValidationWindow.setVisibility(View.VISIBLE);
        flValidationWindow.setVisibility(View.VISIBLE);
        tvProductName.setText("Valeur de la caisse");
        etQuantity.setText(Float.toString(JavaUtil.getCaisse(this)));

    }
}
