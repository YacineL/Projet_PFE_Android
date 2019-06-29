package com.example.projet_pfe_android.validator;

import android.widget.EditText;

import com.example.projet_pfe_android.AddFournisseur;
import com.example.projet_pfe_android.R;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.Validate.notNull;

public class FournisseurValidator {

    public static boolean validateInputForAdding(AddFournisseur addFournisseurActivity) {
        boolean success = true;
        notNull(addFournisseurActivity, "[FournisseurValidator][validateInputForAdding] addFournisseurActivity is null");
        EditText et_nom = addFournisseurActivity.findViewById(R.id.et_nom);
        EditText et_prenom = addFournisseurActivity.findViewById(R.id.et_prenom);
        EditText et_numero_tel = addFournisseurActivity.findViewById(R.id.et_numero_tel);
        EditText et_adresse_mail = addFournisseurActivity.findViewById(R.id.et_adresse_mail);

        if (StringUtils.isBlank(et_nom.getText())) {
            et_nom.setError(addFournisseurActivity.getString(R.string.blank_name_error));
            success = false;
        }
        if (StringUtils.isBlank(et_prenom.getText())) {
            et_prenom.setError(addFournisseurActivity.getString(R.string.blank_first_name_error));
            success = false;
        }
        if (StringUtils.isBlank(et_numero_tel.getText()) && StringUtils.isBlank(et_adresse_mail.getText()) ) {
            et_numero_tel.setError(addFournisseurActivity.getString(R.string.blank_phone_number_error));
            et_adresse_mail.setError(addFournisseurActivity.getString(R.string.blank_phone_number_error));
            success = false;
        }
        return success;
    }
}
