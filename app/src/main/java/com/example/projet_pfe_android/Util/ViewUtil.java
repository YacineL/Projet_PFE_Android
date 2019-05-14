package com.example.projet_pfe_android.Util;

import android.content.Context;
import android.util.TypedValue;

public class ViewUtil {

    public static int dpToPx(Context context, int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp, context.getResources().getDisplayMetrics());
    }
}
