package com.example.projet_pfe_android.CustomViews;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.projet_pfe_android.Adapters.TransactionAdapter;
import com.example.projet_pfe_android.Model.TransactionLine;
import com.example.projet_pfe_android.R;
import com.example.projet_pfe_android.Util.JavaUtil;
import com.example.projet_pfe_android.Util.ViewUtil;
import com.squareup.picasso.Picasso;

public class TransactionLineView extends CardView {

    private TextView tvSeq, tvProductName, tvQty, tvPrice, tvAmount;
    private ImageView ivPicture, ivButton;
    private TransactionLineListener listener;

    public void setTransactionLineListener(TransactionLineListener listener) {
        this.listener = listener;
    }

    public void setViewType(int viewType) {
        switch (viewType) {
            case TransactionAdapter.TABLE:
                ivButton.setVisibility(VISIBLE);
                break;
            case TransactionAdapter.HISTORY:
                ivButton.setVisibility(GONE);
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
        tvPrice.setText(JavaUtil.currencyString(transactionLine.getPrice()));
        tvAmount.setText(JavaUtil.currencyString(transactionLine.getAmount()));
    }

    private void init() {
        tvSeq = this.findViewById(R.id.tv_seq);
        tvProductName = this.findViewById(R.id.tv_product_name);
        tvQty = this.findViewById(R.id.tv_quantity);
        tvPrice = this.findViewById(R.id.tv_price);
        tvAmount = this.findViewById(R.id.tv_amount);
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
