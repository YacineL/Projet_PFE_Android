package com.example.projet_pfe_android.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.projet_pfe_android.Model.Fournisseur;
import com.example.projet_pfe_android.R;
import com.example.projet_pfe_android.Util.ViewUtil;

public class FournisseurView extends CardView {

    private TextView nameF, prenomF, telF;
    private FournisseurViewListener listener;

    public void setFournisseur(Fournisseur fournisseur) {
        nameF.setText(fournisseur.getNom());
        prenomF.setText(fournisseur.getPrenom());
        telF.setText(fournisseur.getNumeroTel());

    }


    public void setFournisseurViewListener(FournisseurViewListener Listener) {
        this.listener = listener;
    }


    public interface FournisseurViewListener {
        void onClick();

        void onAdd();
        //void onEdit();
        //void onDelete();
    }

    public FournisseurView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public FournisseurView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FournisseurView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.fournisseur_view, this, true);
        init();
    }

    private void init() {

        nameF = this.findViewById(R.id.name_fournisseur);
        prenomF = this.findViewById(R.id.prenom_fournisseur);
        telF = this.findViewById(R.id.numeroTel);


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onClick();
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
