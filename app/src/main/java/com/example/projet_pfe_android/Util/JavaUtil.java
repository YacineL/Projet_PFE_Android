package com.example.projet_pfe_android.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telecom.CallAudioState;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;

public class JavaUtil {

    public static final String PRODUCT_ID_KEY = "com.example.projet_pfe_android.Util_PRODUCT_ID_KEY";
    public static final String FOURNISSEUR_ID_KEY = "com.example.projet_pfe_android.Util_FOURNISSEUR_ID_KEY";
    public static final String TRANSACTION_TYPE_KEY = "com.example.projet_pfe_android.Util_TRANSACTION_TYPE_KEY";
    public static final String CAISSE_KEY = "com.example.projet_pfe_android.Util_CAISSE_KEY";
    public static final int NO_RESULT = -1;

    //    Gives proper format to currency values for display
    public static String currencyString(double value) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols symbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        symbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(symbols);
        return nf.format(value);
//        return NumberFormat.getCurrencyInstance().format(value);
    }

    public static void saveCaisse(Context context, float caisseValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(CAISSE_KEY,caisseValue);
        editor.commit();
    }

    public static float getCaisse(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getFloat(CAISSE_KEY,0);
    }

}
