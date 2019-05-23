package com.example.projet_pfe_android.Adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projet_pfe_android.CustomViews.TransactionLineView;
import com.example.projet_pfe_android.Model.TransactionLine;
import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends ListAdapter<TransactionLine, TransactionAdapter.TransactionHolder> {

    private TransactionAdapterListener listener;

    public interface TransactionAdapterListener {
        void onEdit(TransactionLine transactionLine);
    }

    private static DiffUtil.ItemCallback DIFF_UTIL = new DiffUtil.ItemCallback() {
        @Override
        public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return false;
        }
    };

    public TransactionAdapter(TransactionAdapterListener listener) {
        super(DIFF_UTIL);
        this.listener=listener;
    }

    @Override
    public void submitList(@Nullable List<TransactionLine> list) {
        super.submitList(list!=null?new ArrayList<>(list):null);
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TransactionLineView transactionLineView = new TransactionLineView(parent.getContext());
        transactionLineView.setMargin(0,1,0,0);
        return new TransactionHolder(transactionLineView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, final int position) {
        holder.setTransactionLine(getItem(position));
        holder.setTransactionLineListener(new TransactionLineView.TransactionLineListener() {
            @Override
            public void onEdit() {
                listener.onEdit(getItem(position));
            }
        });
    }

    public class TransactionHolder extends RecyclerView.ViewHolder{

        private TransactionLineView transactionLineView;

        public TransactionHolder(@NonNull TransactionLineView transactionLineView) {
            super(transactionLineView);
        }

        public void setTransactionLine(TransactionLine transactionLine){
            transactionLineView.setTransactionLine(transactionLine);
        }

        public Context getContext(){
            return transactionLineView.getContext();
        }

        public void setTransactionLineListener(TransactionLineView.TransactionLineListener listener){
            transactionLineView.setTransactionLineListener(listener);
        }
    }
}
