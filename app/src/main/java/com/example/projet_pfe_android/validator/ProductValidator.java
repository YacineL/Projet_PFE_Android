package com.example.projet_pfe_android.validator;

import android.text.Editable;
import android.widget.EditText;

import com.example.projet_pfe_android.AddProduct;
import com.example.projet_pfe_android.R;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.Validate.notNull;

public class ProductValidator {
    public static boolean validateInputForAdding(AddProduct addProductActivity) {
        boolean success = true;
        EditText et_nom, et_Stock1, et_uom, et_prix_achat, et_prix_vente;
        notNull(addProductActivity, "[ProductValidator][validateInputForAdding] addProductActivity is null");
        et_nom = addProductActivity.findViewById(R.id.et_nom);
        et_Stock1 = addProductActivity.findViewById(R.id.Stock1);
        et_uom = addProductActivity.findViewById(R.id.et_uom);
        et_prix_achat = addProductActivity.findViewById(R.id.et_prix_achat);
        et_prix_vente = addProductActivity.findViewById(R.id.et_prix_vente);

        if (StringUtils.isBlank(et_nom.getText())) {
            et_nom.setError(addProductActivity.getString(R.string.blank_name_error));
            success = false;
        }

        final Editable rawSecurityStock = et_Stock1.getText();
        if (StringUtils.isBlank(rawSecurityStock)) {
            et_Stock1.setError(addProductActivity.getString(R.string.blank_security_threshold_error));
            success = false;
        }
        try {
            double securityStock = Double.parseDouble(rawSecurityStock.toString());
            if (securityStock < 0) {
                success = false;
                et_Stock1.setError(addProductActivity.getString(R.string.negative_number_error));
            }
        } catch (NumberFormatException exception) {
            success = false;
            et_Stock1.setError(addProductActivity.getString(R.string.malformed_security_threshold_error));
        }

        final Editable rawBoughtPrice = et_prix_achat.getText();
        double boughtPrice = -1;
        if (StringUtils.isBlank(rawBoughtPrice)) {
            et_prix_achat.setError(addProductActivity.getString(R.string.blank_bought_price_error));
            success = false;
        }
        try {
            boughtPrice = Double.parseDouble(rawBoughtPrice.toString());
            if (boughtPrice < 0) {
                success = false;
                et_prix_achat.setError(addProductActivity.getString(R.string.negative_number_error));
            }
        } catch (NumberFormatException exception) {
            success = false;
            et_prix_achat.setError(addProductActivity.getString(R.string.malformed_bought_price_error));
        }

        final Editable rawSellingPrice = et_prix_vente.getText();
        if (StringUtils.isBlank(rawSellingPrice)) {
            et_prix_vente.setError(addProductActivity.getString(R.string.blank_selling_price_error));
            success = false;
        }
        try {
            double salePrice = Double.parseDouble(rawSellingPrice.toString());
            if (salePrice < 0) {
                success = false;
                et_prix_vente.setError(addProductActivity.getString(R.string.negative_number_error));
            }
             if (salePrice <= boughtPrice) {
                success = false;
                et_prix_vente.setError(addProductActivity.getString(R.string.inferior_sale_price));
            }
        } catch (NumberFormatException exception) {
            success = false;
            et_prix_vente.setError(addProductActivity.getString(R.string.malformed_selling_price_error));
        }

        if (StringUtils.isBlank(et_uom.getText())) {
            et_uom.setError(addProductActivity.getString(R.string.blank_uom_error));
            success = false;
        }
        return success;

    }
}
