package com.example.projet_pfe_android.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_pfe_android.Model.Fournisseur;

public class FournisseurAdapter extends ListAdapter<Fournisseur, FournisseurAdapter.FournisseurHolder> {

    private static DiffUtil.ItemCallback DIFF_UTIL = new DiffUtil.ItemCallback<Fournisseur>() {

        @Override
        public boolean areItemsTheSame(@NonNull Fournisseur oldItem, @NonNull Fournisseur newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Fournisseur oldItem, @NonNull Fournisseur newItem) {
            return false;
        }
    };

    protected FournisseurAdapter(@NonNull DiffUtil.ItemCallback<Fournisseur> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public FournisseurHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FournisseurHolder holder, int position) {

    }

    public class FournisseurHolder extends RecyclerView.ViewHolder{

        public FournisseurHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
