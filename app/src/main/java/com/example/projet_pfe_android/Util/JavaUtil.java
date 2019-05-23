package com.example.projet_pfe_android.Util;

import java.text.NumberFormat;

public class JavaUtil {

//    Gives proper format to currency values for display
    public static String currencyString(double value){
        return NumberFormat.getCurrencyInstance().format(value);
    }
}
