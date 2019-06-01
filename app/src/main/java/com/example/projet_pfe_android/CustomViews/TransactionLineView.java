package com.example.projet_pfe_android.CustomViews;

import android.content.Context;
import android.hardware.GeomagneticField;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.projet_pfe_android.Adapters.TransactionAdapter;
import com.example.projet_pfe_android.Model.Transaction;
import com.example.projet_pfe_android.Model.TransactionLine;
import com.example.projet_pfe_android.R;
import com.example.projet_pfe_android.Util.JavaUtil;
import com.example.projet_pfe_android.Util.ViewUtil;
import com.squareup.picasso.Picasso;

public class TransactionLineView extends CardView {

    private TextView tvSeq, tvProductName, tvQty, tvPrice, tvAmount, tvTransactionLabel, tvTransactionId;
    private ImageView ivPicture, ivButton;
    private TransactionLineListener listener;
    private int viewType;

    public void setTransactionLineListener(TransactionLineListener listener) {
        this.listener = listener;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
        switch (viewType) {
            case TransactionAdapter.TABLE:
                ivButton.setVisibility(VISIBLE);
                tvTransactionLabel.setVisibility(GONE);
                tvTransactionId.setVisibility(GONE);
                break;
            case TransactionAdapter.HISTORY:
                ivButton.setVisibility(GONE);
                tvTransactionLabel.setVisibility(VISIBLE);
                tvTransactionId.setVisibility(VISIBLE);
                break;
        }
    }

    public interface TransactionLineListener {
        void onEdit();
    }

    public TransactionLineView(@NonNull Context context) {
        this(context, null);
    }

    public TransactionLineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransactionLineView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.transaction_line_view, this, true);
        init();
    }

    public void setTransactionLine(TransactionLine transactionLine) {
        tvSeq.setText(Integer.toString(transactionLine.getSeq()));
        try {
            Picasso.get().load(Uri.parse(transactionLine.getPictureUri())).into(ivPicture);
//            ivPicture.setImageURI(Uri.parse(transactionLine.getPictureUri()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        tvProductName.setText(transactionLine.getProductName());
        tvQty.setText(Float.toString(transactionLine.getQuantity()));

        switch (transactionLine.getType()) {
            case Transaction.TYPE_VENTE:
                tvPrice.setText(JavaUtil.currencyString(transactionLine.getSalesPrice()));
                Log.d("price#", "Sales Price: "+transactionLine.getSalesPrice());
                break;
            case Transaction.TYPE_RECEPTION:
                tvPrice.setText(JavaUtil.currencyString(transactionLine.getPrice()));
                Log.d("price#", "Unit Price: "+transactionLine.getPrice());
                break;
        }
        tvAmount.setText(JavaUtil.currencyString(transactionLine.getAmount()));
        if (viewType == TransactionAdapter.HISTORY)
            tvTransactionId.setText(Integer.toString(transactionLine.getTransactionId()));
    }

    private void init() {
        tvSeq = this.findViewById(R.id.tv_seq);
        tvProductName = this.findViewById(R.id.tv_product_name);
        tvQty = this.findViewById(R.id.tv_quantity);
        tvPrice = this.findViewById(R.id.tv_price);
        tvAmount = this.findViewById(R.id.tv_amount);
        tvTransactionId = this.findViewById(R.id.tv_tnx);
        tvTransactionLabel = this.findViewById(R.id.tv_label_transaction);
        ivPicture = this.findViewById(R.id.iv_picture);
        ivButton = this.findViewById(R.id.iv_button);

        ivButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEdit();
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
