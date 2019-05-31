package com.example.projet_pfe_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.projet_pfe_android.Adapters.TransactionAdapter;
import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Model.Transaction;
import com.example.projet_pfe_android.Model.TransactionLine;
import com.example.projet_pfe_android.Util.JavaUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CurrentTransactionActivity extends AppCompatActivity {

    private AppViewModel viewModel;
    private TransactionAdapter adapter;
    private int type;
    private List<TransactionLine> transactionLines;
    private double totalAmount;

    private TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_transaction);

        init();

        setupViewModel();

        setupRecycler();
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        viewModel.getCurrentTransactionProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                transactionLines = TransactionLine.toTransactionLine(products, type);
                setTotalAmount();
                adapter.submitList(transactionLines);
            }
        });

        viewModel.getAllTransactionLines().observe(this, new Observer<List<TransactionLine>>() {
            @Override
            public void onChanged(List<TransactionLine> transactionLines) {
                for (TransactionLine t:transactionLines)
                    Log.d("tnx", t.toString());
            }
        });
    }

    private void init() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Transaction");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_home);

        Spinner spinner = findViewById(R.id.sp_type);
        type = getIntent().getIntExtra(JavaUtil.TRANSACTION_TYPE_KEY, JavaUtil.NO_RESULT);
        if (type != JavaUtil.NO_RESULT) {
            spinner.setSelection(type);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurrentTransactionActivity.this, ListProduit.class);
                startActivity(intent);
            }
        });

        tvTotalAmount = findViewById(R.id.tv_total);
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
        getMenuInflater().inflate(R.menu.transaction_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.validate:
                // TODO: 29/05/2019 [for Yacine] implement a SnackBar to confirm validation
                commitTransaction();
                break;
            case R.id.empty_transaction:
                // TODO: 29/05/2019 [for Yacine] SnackBar to confirm deleting all transaction lines from current transaction
                viewModel.emptyCurrentTransaction();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void commitTransaction() {
//         setTransactionDate, totalAmount
        viewModel.getCurrentTransaction().setDate(Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis());
        viewModel.getCurrentTransaction().setTotalAmount(totalAmount);
        viewModel.getCurrentTransaction().setType(type);

//         insert Transaction and get its id
        int transactionId = viewModel.insertCurrentTransaction();

//         set transactionId in lines

        for (TransactionLine t : transactionLines)
            t.setTransactionId(transactionId);

//         insert lines
        viewModel.insertTransactionLines(transactionLines);

//         update products' availableQty
//        if (type==Transaction.TYPE_VENTE) {
//            for (TransactionLine t : transactionLines)
//                t.reverseQty();
//        }
//        viewModel.commitTransactionLines(transactionLines);

        List<Product> products = viewModel.getCurrentTransactionProducts().getValue();
        int i = (type==Transaction.TYPE_VENTE)?-1:1;
        for (Product p : products)
            for (TransactionLine t:transactionLines)
                if (p.getId()==t.getProductId())
                    p.setAvailableQty(p.getAvailableQty()+i*t.getQuantity());
        viewModel.updateProducts(products);

//        Empty current transaction and reinitialize products transactionQty
        viewModel.emptyCurrentTransaction();
    }

    public void setTotalAmount() {
        if (transactionLines.size() == 0) {
            totalAmount = 0;
            tvTotalAmount.setText(JavaUtil.currencyString(totalAmount));
            return;
        }

        double totalAmount = 0;
        for (TransactionLine t : transactionLines) {
            totalAmount += t.amount;
        }
        this.totalAmount = totalAmount;
        tvTotalAmount.setText(JavaUtil.currencyString(totalAmount));
    }
}
