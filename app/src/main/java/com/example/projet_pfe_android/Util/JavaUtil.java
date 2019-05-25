package com.example.projet_pfe_android.Util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class JavaUtil {

    public static final String PRODUCT_ID_KEY = "com.example.projet_pfe_android.Util_PRODUCT_ID_KEY";
    public static final int NO_RESULT = -1;

//    Gives proper format to currency values for display
    public static String currencyString(double value){
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols symbols = ((DecimalFormat)nf).getDecimalFormatSymbols();
        symbols.setCurrencySymbol("");
        ((DecimalFormat)nf).setDecimalFormatSymbols(symbols);
        return nf.format(value);
//        return NumberFormat.getCurrencyInstance().format(value);
    }
}
