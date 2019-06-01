package com.example.projet_pfe_android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.projet_pfe_android.Model.Transaction;
import com.example.projet_pfe_android.Util.JavaUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppViewModel viewModel;

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
        tvProduitsSS = findViewById(R.id.tv_produits_ss);
        tvProduitsZS = findViewById(R.id.tv_produits_zs);

        CardView cvCaisse = findViewById(R.id.caisse);
        cvCaisse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Go to Edit caisse
            }
        });

        float caisseValue = JavaUtil.getCaisse(this);
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
}
