package com.example.projet_pfe_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projet_pfe_android.Adapters.TransactionAdapter;
import com.example.projet_pfe_android.Model.TransactionLine;

import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private AppViewModel viewModel;
    private TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupRecycler();

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.getAllTransactionLines().observe(this, new Observer<List<TransactionLine>>() {
            @Override
            public void onChanged(List<TransactionLine> transactionLines) {
                adapter.submitList(transactionLines);
            }
        });
    }

    private void setupRecycler() {
        adapter = new TransactionAdapter(new TransactionAdapter.TransactionAdapterListener() {
            @Override
            public void onEdit(TransactionLine transactionLine) {

            }
        });
        adapter.setType(TransactionAdapter.HISTORY);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
