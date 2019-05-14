package com.example.projet_pfe_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projet_pfe_android.Adapters.ProductAdapter;
import com.example.projet_pfe_android.CustomViews.ProductView;
import com.example.projet_pfe_android.Data.Database;
import com.example.projet_pfe_android.Data.ProductDao;
import com.example.projet_pfe_android.Model.Product;

import java.util.List;

public class ListProduit extends AppCompatActivity {

    private ProductAdapter adapter;
    private AppViewModel viewModel;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produit);
        getSupportActionBar().setTitle("Produits");

        repository = new Repository(getApplication());
        repository.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.submitList(products);
            }
        });

        setupRecyclerView();

//  App crashes when I use ViewModel - Currently bypassing it - Looking for a solution
//        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
//        viewModel.init();
//        viewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
//            @Override
//            public void onChanged(List<Product> products) {
//                adapter.submitList(products);
//            }
//        });

        createDummyList();
    }

    private void createDummyList() {
        repository.deleteAllProducts();

        repository.insertProduct(new Product("CocaCola Canette",40));
        repository.insertProduct(new Product("CocaCola 1.5L",89));
        repository.insertProduct(new Product("CocaCola 2L",75));
        repository.insertProduct(new Product("CocaCola 250cl",37));
        repository.insertProduct(new Product("Le Chat 5Kg",29));


//  App crashes when I use ViewModel - Currently bypassing it - Looking for a solution
//        viewModel.deleteAllProducts();
//
//        viewModel.insertProduct(new Product("CocaCola Canette",40));
//        viewModel.insertProduct(new Product("CocaCola 1.5L",89));
//        viewModel.insertProduct(new Product("CocaCola 2L",75));
//        viewModel.insertProduct(new Product("CocaCola 250cl",37));
//        viewModel.insertProduct(new Product("Le Chat 5Kg",29));
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rc_product_list);

        adapter = new ProductAdapter(null); //Null to be replaced with the proper adapter listener ones implemented

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
