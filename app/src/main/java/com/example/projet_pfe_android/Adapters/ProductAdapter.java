package com.example.projet_pfe_android.Adapters;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_pfe_android.CustomViews.ProductView;
import com.example.projet_pfe_android.Model.Product;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductHolder> implements Filterable {

    private List<Product> products;
    private ProductAdapterListener productAdapterListener;

    public ProductAdapter(ProductAdapterListener listener) {
        super(DIFF_UTIL);
        productAdapterListener = listener;
    }

    private static DiffUtil.ItemCallback DIFF_UTIL = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };

    public void setAdapterListener(ProductAdapterListener listener){
        productAdapterListener = listener;
    }
    public interface ProductAdapterListener{
        void onAdd(Product product);
        void onClick(Product product);
    }

    @Override
    public void submitList(@Nullable List<Product> list) {
        products = list;
        super.submitList(list!=null ? new ArrayList<>(list):null);
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductView productView = new ProductView(parent.getContext());
        productView.setMargin(2,2,2,2);
        return new ProductHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductHolder holder, final int position) {
        holder.setProduct(getItem(position));
        holder.setListener(new ProductView.ProductViewListener() {
            @Override
            public void onClick() {
//                Execute the corresponding method from adapter's custom listener
                if (getItem(position)!=null)
                    productAdapterListener.onClick(getItem(position));
            }

            @Override
            public void onAdd() {
                if (getItem(position)!=null)
                    productAdapterListener.onAdd(getItem(position));
            }
        });
    }

    public class ProductHolder extends RecyclerView.ViewHolder{

        private ProductView productView;

        public ProductHolder(@NonNull ProductView productView) {
            super(productView);
            this.productView = productView;
        }

        public ProductView getProductView() {
            return productView;
        }

        public void setProduct(Product product){
            productView.setProduct(product);
        }

        public void setListener(ProductView.ProductViewListener listener){
            productView.setProductViewListener(listener);
        }

        public Context getContext(){
            return productView.getContext();
        }
    }

//    For search field to function ...

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Product> filteredList = products;
            if (StringUtils.isNotBlank(charSequence)) {
                filteredList = new ArrayList<>();
                String criteria = charSequence.toString().toLowerCase().trim();
                for (Product product : products) {
                    for (String matchable : Arrays.asList(product.getName(), product.getDescription(), product.getSerialNumber(), product.getBrand())) {
                        if (StringUtils.containsIgnoreCase(matchable, criteria)) {
                            filteredList.add(product);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<Product> fullList = new ArrayList<>(products);
            submitList((List)filterResults.values);
            products.clear();
            products.addAll(fullList);
        }
    };
}
