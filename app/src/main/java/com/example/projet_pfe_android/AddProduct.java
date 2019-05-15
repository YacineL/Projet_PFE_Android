package com.example.projet_pfe_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.example.projet_pfe_android.Model.Product;

import java.util.List;

public class AddProduct extends AppCompatActivity {

    private AppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Toast.makeText(AddProduct.this, "onChanged:"+Integer.toString(products.size()), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
