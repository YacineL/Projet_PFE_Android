package com.example.projet_pfe_android.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_pfe_android.AddProduct;
import com.example.projet_pfe_android.Add_fournisseur;
import com.example.projet_pfe_android.CustomViews.FournisseurView;
import com.example.projet_pfe_android.Model.Fournisseur;
import java.util.ArrayList;
import java.util.List;

public class FournisseurAdapter extends ListAdapter<Fournisseur, FournisseurAdapter.FournisseurHolder> implements Filterable {

    private List<Fournisseur> fournisseurs;
    private FournisseurAdapterListener fournisseurAdapterListener;

    public FournisseurAdapter(FournisseurAdapterListener listener) {
        super(DIFF_UTIL);
        fournisseurAdapterListener = listener;
    }

    private static DiffUtil.ItemCallback DIFF_UTIL = new DiffUtil.ItemCallback<Fournisseur>() {

        @Override
        public boolean areItemsTheSame(@NonNull Fournisseur oldItem, @NonNull Fournisseur newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Fournisseur oldItem, @NonNull Fournisseur newItem) {
            return oldItem.equals(newItem);
        }
    };

    public void setAdapterListener(FournisseurAdapterListener listener) {
        fournisseurAdapterListener = listener;
    }

    public interface FournisseurAdapterListener {
        void onCall(Fournisseur fournisseur);

        void onMail(Fournisseur fournisseur);
    }

    @Override
    public void submitList(@Nullable List<Fournisseur> list) {
        fournisseurs = list;
        super.submitList(list != null ? new ArrayList<>(list) : null);
    }

    @NonNull
    @Override
    public FournisseurHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FournisseurView fournisseurView = new FournisseurView(parent.getContext());
        fournisseurView.setMargin(2, 2, 2, 2);
        return new FournisseurHolder(fournisseurView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FournisseurHolder holder, final int position) {
        holder.setFournisseur(getItem(position));
        holder.setListener(new FournisseurView.FournisseurViewListener() {
            @Override
            public void onClick() {
                Context context=holder.getContext();
                Intent intent=new Intent(context, Add_fournisseur.class);
                context.startActivity(intent);
                Toast.makeText(holder.getContext(), "Click !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCall() {
                if (getItem(position) != null)
                    fournisseurAdapterListener.onCall(getItem(position));
            }

            @Override
            public void onMail(){
                fournisseurAdapterListener.onMail(getItem(position));
            }
        });
    }

    public class FournisseurHolder extends RecyclerView.ViewHolder {

        private FournisseurView fournisseurView;


        public FournisseurHolder(@NonNull FournisseurView fournisseurView) {
            super(fournisseurView);
            this.fournisseurView = fournisseurView;
        }

        public FournisseurView getFournisseurView() {
            return fournisseurView;
        }

        public void setFournisseur(Fournisseur fournisseur) {
            fournisseurView.setFournisseur(fournisseur);
        }

        public void setListener(FournisseurView.FournisseurViewListener listener) {
            fournisseurView.setFournisseurViewListener(listener);
        }

        public Context getContext() {
            return fournisseurView.getContext();
        }

    }

    // Recherche

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Fournisseur> filteredList = new ArrayList<>();
            String criteria = charSequence.toString().toLowerCase().trim();
            if (charSequence == null || charSequence.length() == 0)
                filteredList = fournisseurs;
            else
                for (Fournisseur fournisseur : fournisseurs)
                    if (fournisseur.getNom().toLowerCase().trim().contains(criteria))
                        filteredList.add(fournisseur);


            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<Fournisseur> fullList = new ArrayList<>(fournisseurs);
            submitList((List) filterResults.values);
            fournisseurs.clear();
            fournisseurs.addAll(fullList);
        }
    };

}
