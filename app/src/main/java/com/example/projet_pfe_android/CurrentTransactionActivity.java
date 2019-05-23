package com.example.projet_pfe_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projet_pfe_android.Adapters.TransactionAdapter;
import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Model.TransactionLine;

import java.util.List;

public class CurrentTransactionActivity extends AppCompatActivity {

    private AppViewModel viewModel;
    private TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_transaction);
        getSupportActionBar().setTitle("Transaction");

        viewModel.getCurrentTransactionProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

                adapter.submitList(TransactionLine.toTransactionLine(products,viewModel.getCurrentTransaction().getType()));
            }
        });

        setupRecycler();

    }

    private void setupRecycler() {
        adapter = new TransactionAdapter(new TransactionAdapter.TransactionAdapterListener() {
            @Override
            public void onEdit(TransactionLine transactionLine) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
