package com.example.projet_pfe_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projet_pfe_android.Model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class AddProduct extends AppCompatActivity {

    private AppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        viewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Toast.makeText(AddProduct.this, "onChanged:"+Integer.toString(products.size()), Toast.LENGTH_SHORT).show();
            }
        });

        /*EditText et_nom = (EditText)findViewById(R.id.et_nom);
        EditText et_brand = (EditText)findViewById(R.id.et_brand);
        EditText et_description = (EditText)findViewById(R.id.et_description);
        EditText et_Stock1 = (EditText)findViewById(R.id.Stock1);
        EditText et_Stockop = (EditText)findViewById(R.id.Stockop);
        EditText et_uom = (EditText)findViewById(R.id.et_uom);
        EditText et_prix_achat = (EditText)findViewById(R.id.et_prix_achat);
        EditText et_prix_vente = (EditText)findViewById(R.id.et_prix_vente);
        EditText et_numS = (EditText)findViewById(R.id.NumS1);*/
        ImageButton bnumS =(ImageButton) findViewById(R.id.BnumS);
        ImageView iv_picture=(ImageView)findViewById(R.id.iv_picture);
        verifyPermissions();
        bnumS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(AddProduct.this).initiateScan();
            }
        });
        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(AddProduct.this);
                CharSequence items[] = new CharSequence[] {"Camera", "Gallerie"};
                adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface d, int n) {
                        switch (n){
                            case 0:
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                                break;
                            case 1:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                                break;
                        }
                    }

                });
                adb.setNegativeButton("Annuler", null);
                adb.setTitle("Où prendre la photo");
                adb.show();
                adb.setCancelable(true);

            }
        });

    }
    private void verifyPermissions(){
        Log.d("MainActivity", "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){
            ;
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    REQUEST_CODE);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageview=(ImageView)findViewById(R.id.iv_picture);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    imageview.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    imageview.setImageURI(selectedImage);
                }
                break;
            case 0x0000c0de: //REQUEST_CODE of IntentIntegrator of the zxing library
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if(result != null) {
                    if(result.getContents() == null) {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        EditText et_numS = (EditText)findViewById(R.id.NumS1);
                        et_numS.setText(result.getContents());
                    }
                } else {
                    // This is important, otherwise the result will not be passed to the fragment
                    super.onActivityResult(requestCode, resultCode, data);
                }
                break;

        }
    }


}
