package com.example.projet_pfe_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.projet_pfe_android.Adapters.FournisseurAdapter;
import com.example.projet_pfe_android.Model.Fournisseur;
import com.example.projet_pfe_android.Model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListFournisseur extends AppCompatActivity {

    private androidx.appcompat.widget.SearchView searchView;
    private FournisseurAdapter adapter;
    private AppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fournisseur);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fournisseurs");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupRecyclerView();
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.getAllFournisseurs().observe(this, new Observer<List<Fournisseur>>() {
            @Override
            public void onChanged(List<Fournisseur> fournisseurs) {
                adapter.submitList(fournisseurs);
                adapter.notifyDataSetChanged();
                Toast.makeText(ListFournisseur.this, "Fournisseurs : " + fournisseurs.size(), Toast.LENGTH_SHORT).show();
            }
        });

        //viewModel.deleteAllFournisseurs();
        //createDummyList();
        FloatingActionButton fab=findViewById(R.id.fab_fournisseur);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListFournisseur.this,Add_fournisseur.class);
                startActivityForResult(intent,0);
            }
        });
    }

    private void createDummyList() {

        viewModel.insertFournisseur(new Fournisseur("ait","prénom","000000","as@gmail.com","rue","ville","pays"));
        viewModel.insertFournisseur(new Fournisseur("impe","prénom","111111","bs@gmail.com","rue","ville","pays"));
        viewModel.insertFournisseur(new Fournisseur("yac","prénom","222222","cc@gmail.com","rue","ville","pays"));
    }

    private void setupRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recycler);
        adapter = new FournisseurAdapter(new FournisseurAdapter.FournisseurAdapterListener() {
            @Override
            public void onCall(Fournisseur fournisseur) {
                Toast.makeText(ListFournisseur.this, "Fournisseur "+fournisseur.getNom(), Toast.LENGTH_SHORT).show();
                Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+fournisseur.getNumeroTel()));
                startActivity(dial);
            }

            @Override
            public void onMail(Fournisseur fournisseur) {
                Toast.makeText(ListFournisseur.this, "Fournisseur "+fournisseur.getNom(), Toast.LENGTH_SHORT).show();
                Intent email = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"+fournisseur.getEmailFournisseur()));
                startActivity(email);

            }


        });


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ListFournisseur.this.viewModel.deleteFournisseur(ListFournisseur.this.viewModel.getAllFournisseurs().getValue().get(position));
                    Toast.makeText(ListFournisseur.this, "Swiped !", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fournisseur_list_menu, menu);
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
            case R.id.search:

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0)
        {
            setupRecyclerView();

            viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
            viewModel.getAllFournisseurs().observe(this, new Observer<List<Fournisseur>>() {
                @Override
                public void onChanged(List<Fournisseur> fournisseurs) {
                    adapter.submitList(fournisseurs);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ListFournisseur.this, "Produits : " + fournisseurs.size(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

