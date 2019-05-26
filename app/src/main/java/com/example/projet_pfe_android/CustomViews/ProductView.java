package com.example.projet_pfe_android.CustomViews;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.R;
import com.example.projet_pfe_android.Util.ViewUtil;
import com.squareup.picasso.Picasso;

public class ProductView extends CardView {

    private TextView tvName, tvStock, tvTransactionQty;
    private ImageView ivProductImg, ivButton, ivCaddy;
    private ProductViewListener listener;

    public void setProduct(Product product) {
        tvName.setText(product.getName());
        tvStock.setText(Float.toString(product.getAvailableQty()));
        Picasso.get().load(Uri.parse(product.pictureURI)).into(ivProductImg);
//        ivProductImg.setImageURI(Uri.parse(product.getPictureURI()));
        if (product.getTransactionQty()!=0){
            tvTransactionQty.setText(Float.toString(product.getTransactionQty()));
            tvTransactionQty.setVisibility(VISIBLE);
            ivCaddy.setVisibility(VISIBLE);
        }else{
            tvTransactionQty.setVisibility(INVISIBLE);
            ivCaddy.setVisibility(INVISIBLE);
        }
    }

    public void setProductViewListener(ProductViewListener listener){
        this.listener = listener;
    }

    public interface ProductViewListener{
        void onClick();
        void onAdd();
        //void onEdit();
        //void onDelete();
    }

    public ProductView(@NonNull Context context) {
        this(context,null);
    }

    public ProductView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ProductView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.product_view,this,true);
        init();
    }

    private void init() {

        tvName = this.findViewById(R.id.tv_product_name);
        tvStock = this.findViewById(R.id.tv_stock);
        tvTransactionQty = this.findViewById(R.id.tv_transaction_qty);
        ivProductImg = this.findViewById(R.id.iv_product_img);
        ivButton = this.findViewById(R.id.iv_button);
        ivCaddy = this.findViewById(R.id.iv_caddy);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onClick();
            }
        });

        ivButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onAdd();
            }
        });

    }

    public void setMargin(int top, int bottom, int left, int right) {
//        this.setMargin(top, bottom, left, right);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(
                ViewUtil.dpToPx(this.getContext(), left),
                ViewUtil.dpToPx(this.getContext(), top),
                ViewUtil.dpToPx(this.getContext(), right),
                ViewUtil.dpToPx(this.getContext(), bottom));
        this.setLayoutParams(params);
    }
}
