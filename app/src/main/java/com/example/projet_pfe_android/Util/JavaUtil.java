package com.example.projet_pfe_android.Util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;

public class JavaUtil {

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
