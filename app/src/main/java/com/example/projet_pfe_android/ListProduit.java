package com.example.projet_pfe_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_pfe_android.Adapters.ProductAdapter;
import com.example.projet_pfe_android.Model.Product;

import java.util.List;

public class ListProduit extends AppCompatActivity {

    private ProductAdapter adapter;
    private AppViewModel viewModel;

    private ConstraintLayout clValidationWindow;
    private TextView tvProductName;
    private AppCompatEditText etQuantity;
    private Button bValider, bAnnuler;

    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produit);
        getSupportActionBar().setTitle("Produits");

        setupRecyclerView();

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.submitList(products);
                Toast.makeText(ListProduit.this, "Produits : "+products.size(), Toast.LENGTH_SHORT).show();
            }
        });

        setupValidationWindow();

//        createDummyList();
    }

    private void createDummyList() {
        viewModel.insertProduct(new Product("CocaCola 1.5L",70));
        viewModel.insertProduct(new Product("CocaCola Canette",70));
        viewModel.insertProduct(new Product("CocaCola 2L",70));
        viewModel.insertProduct(new Product("CocaCola 0.25L",70));
        viewModel.insertProduct(new Product("Le chat 500g",70));
        viewModel.insertProduct(new Product("Le chat 5Kg",70));
        viewModel.insertProduct(new Product("Le chien !",70));
        viewModel.insertProduct(new Product("Milka",70));
    }

    private void setupValidationWindow(){
        clValidationWindow = findViewById(R.id.cl_validation_window);
        tvProductName = findViewById(R.id.tv_nom_produit);
        etQuantity = findViewById(R.id.et_quantity);
        bValider = findViewById(R.id.b_valider);
        bAnnuler = findViewById(R.id.b_annuler);

        bValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add product with qty to transaction list
                try{
                    float qty = Float.parseFloat(etQuantity.getText().toString());
                    if (selectedProduct!=null){
                        selectedProduct.setTransactionQty(qty);
                        viewModel.addToCurrentTransaction(selectedProduct);
                    }
                    Toast.makeText(ListProduit.this, "Produit ajouté", Toast.LENGTH_SHORT).show();
                }catch (NumberFormatException e){
                    Log.d("#EXP", "ListProduit.bValider.onClick: "+e.getMessage());
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

    private void hideValidationWindow(){
        clValidationWindow.setVisibility(View.GONE);
    }

    private void showValidationWindow(){
        clValidationWindow.setVisibility(View.VISIBLE);
        tvProductName.setText(selectedProduct.getName());
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rc_product_list);

//        AdapterListener, onAdd, set selected to current product
        adapter = new ProductAdapter(new ProductAdapter.ProductAdapterListener() {
            @Override
            public void onAdd(Product product) {
                selectedProduct = product;
                showValidationWindow();
            }
        }); //Null to be replaced with the proper adapter listener ones implemented

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_list_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();

        searchView.setQueryHint("Recherche...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.search:

        }
        return super.onOptionsItemSelected(item);
    }
}