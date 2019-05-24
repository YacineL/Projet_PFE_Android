package com.example.projet_pfe_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.projet_pfe_android.Adapters.TransactionAdapter;
import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Model.TransactionLine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CurrentTransactionActivity extends AppCompatActivity {

    private AppViewModel viewModel;
    private TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_transaction);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Transaction");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);


        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.getCurrentTransactionProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Toast.makeText(CurrentTransactionActivity.this, "products : "+products.size(), Toast.LENGTH_SHORT).show();
                adapter.submitList(TransactionLine.toTransactionLine(products,viewModel.getCurrentTransaction().getType()));
            }
        });

        setupRecycler();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentTransactionActivity.this,ListProduit.class);
                startActivity(intent);
            }
        });

    }

    private void setupRecycler() {
        adapter = new TransactionAdapter(new TransactionAdapter.TransactionAdapterListener() {
            @Override
            public void onEdit(TransactionLine transactionLine) {
                Toast.makeText(CurrentTransactionActivity.this, "Edit !", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.transaction_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.validate:

                break;
            case R.id.empty_transaction:
                viewModel.emptyCurrentTransaction();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
