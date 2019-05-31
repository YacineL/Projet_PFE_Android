package com.example.projet_pfe_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.projet_pfe_android.Adapters.TransactionAdapter;
import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Model.Transaction;
import com.example.projet_pfe_android.Model.TransactionLine;
import com.example.projet_pfe_android.Util.JavaUtil;

import java.util.List;

public class TransactionHistoryActivity extends AppCompatActivity {

    private AppViewModel viewModel;
    private TransactionAdapter adapter;
    private LiveData<List<Product>> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setupRecycler();

        setupViewModel();
    }

    private void setupViewModel() {

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        int type = getIntent().getIntExtra(JavaUtil.TRANSACTION_TYPE_KEY,JavaUtil.NO_RESULT);
        Toast.makeText(this, "Type: "+type, Toast.LENGTH_LONG).show();
        switch(type){
            case Transaction.TYPE_RECEPTION:
                setTitle("Réceptions");
                loadReceivingTransactions();
                break;
            default:
                setTitle("Ventes");
                loadSalesTransactions();
        }
    }

    private void loadSalesTransactions() {
        viewModel.getSalesTransactions().observe(this, new Observer<List<TransactionLine>>() {
            @Override
            public void onChanged(List<TransactionLine> transactionLines) {
                adapter.submitList(transactionLines);
            }
        });
    }

    private void loadReceivingTransactions() {
        viewModel.getReceivingTransactions().observe(this, new Observer<List<TransactionLine>>() {
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
