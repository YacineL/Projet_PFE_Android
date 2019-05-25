package com.example.projet_pfe_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_pfe_android.Adapters.ProductAdapter;
import com.example.projet_pfe_android.Model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListProduit extends AppCompatActivity {

    private ProductAdapter adapter;
    private AppViewModel viewModel;

    private ConstraintLayout clValidationWindow;
    private FrameLayout flValidationWindow;
    private TextView tvProductName;
    private AppCompatEditText etQuantity;
    private Button bValider, bAnnuler;
    private androidx.appcompat.widget.SearchView searchView;
    private Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Produits");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);

        setupRecyclerView();

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.submitList(products);
                adapter.notifyDataSetChanged();
                Toast.makeText(ListProduit.this, "Produits : " + products.size(), Toast.LENGTH_SHORT).show();
            }
        });

        setupValidationWindow();

        //viewModel.deleteAllProducts();
//        createDummyList();
//        createDummyList();
        createDummyList();
        createDummyList();
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_produit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListProduit.this,AddProduct.class);
                startActivityForResult(intent,0);
            }
        });
    }

    private void createDummyList() {
        viewModel.insertProduct(new Product("CocaCola 1.5L", 2));
        viewModel.insertProduct(new Product("CocaCola Canette", 3));
//        viewModel.insertProduct(new Product("CocaCola 2L", 70));
//        viewModel.insertProduct(new Product("CocaCola 0.25L", 70));
//        viewModel.insertProduct(new Product("Le chat 500g", 70));
//        viewModel.insertProduct(new Product("Le chat 5Kg", 70));
//        viewModel.insertProduct(new Product("Le chien !", 70));
//        viewModel.insertProduct(new Product("Milka", 70));
    }

    private void setupValidationWindow() {
        clValidationWindow = findViewById(R.id.cl_validation_window);
        flValidationWindow = findViewById(R.id.fl_validation_window);
        tvProductName = findViewById(R.id.tv_nom_produit);
        etQuantity = findViewById(R.id.et_quantity);
        bValider = findViewById(R.id.b_valider);
        bAnnuler = findViewById(R.id.b_annuler);

        bValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add product with qty to transaction list
                try {
                    float qty = Float.parseFloat(etQuantity.getText().toString());
                    if (selectedProduct != null) {
                        selectedProduct.setTransactionQty(qty);
//                        viewModel.addToCurrentTransaction(selectedProduct);
                        viewModel.updateProduct(selectedProduct);
                        searchView.setQuery("", false);
                        Toast.makeText(ListProduit.this, "Update ID : " + selectedProduct.getId(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(ListProduit.this, "Transaction mise à jour.", Toast.LENGTH_SHORT).show();
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
        tvProductName.setText(selectedProduct.getName());
        etQuantity.setText(Float.toString(selectedProduct.getTransactionQty()));
    }

    private void setupRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.rc_product_list);

//        AdapterListener, onCall, set selected to current product
        adapter = new ProductAdapter(new ProductAdapter.ProductAdapterListener() {
            @Override
            public void onAdd(Product product) {
                selectedProduct = product;
                showValidationWindow();
            }

            @Override
            public void onClick(Product product) {
                Intent intent = new Intent(ListProduit.this, AddProduct.class);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    viewModel.deleteProduct(viewModel.getAllProducts().getValue().get(position));
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
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
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent = new Intent(ListProduit.this, CurrentTransactionActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0){
            setupRecyclerView();

            viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
            viewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    adapter.submitList(products);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ListProduit.this, "Produits : " + products.size(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
