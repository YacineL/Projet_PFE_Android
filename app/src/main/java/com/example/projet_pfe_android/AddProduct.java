package com.example.projet_pfe_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Util.AndroidUtil;
import com.example.projet_pfe_android.Util.JavaUtil;
import com.example.projet_pfe_android.validator.ProductValidator;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddProduct extends AppCompatActivity {

    private EditText et_nom, et_brand, et_description, et_Stock1, et_Stockop, et_uom, et_prix_achat, et_prix_vente, et_numS;
    private ImageButton bnumS;
    private ImageView iv_picture;
    private AppViewModel viewModel;
    private int productId;
    public Uri imagepath;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        et_nom = findViewById(R.id.et_nom);
        et_brand = findViewById(R.id.et_brand);
        et_description = findViewById(R.id.et_description);
        et_Stock1 = findViewById(R.id.Stock1);
        et_uom = findViewById(R.id.et_uom);
        et_prix_achat = findViewById(R.id.et_prix_achat);
        et_prix_vente = findViewById(R.id.et_prix_vente);
        et_numS = findViewById(R.id.NumS1);
        bnumS = findViewById(R.id.BnumS);
        iv_picture = findViewById(R.id.iv_picture);

        Intent intent = getIntent();

        productId = intent.getIntExtra(JavaUtil.PRODUCT_ID_KEY, JavaUtil.NO_RESULT);

        if (productId == JavaUtil.NO_RESULT) {
            setTitle("Ajouter produit");
            invalidateOptionsMenu();
        } else {
            setTitle("Modifier produit");
            product = viewModel.getProductById(productId);
            if (product != null) {
                et_nom.setText(product.getName());
                et_brand.setText(product.getBrand());
                et_description.setText(product.getDescription());
                et_Stock1.setText(String.valueOf(product.getSafetyStockQty()));
                et_uom.setText(product.getUOM());
                et_prix_achat.setText(String.valueOf(product.getUnitPrice()));
                et_prix_vente.setText(String.valueOf(product.getSalePrice()));
                final String pictureURI = product.getPictureURI();
                et_numS.setText(product.getSerialNumber());
                if (StringUtils.isNotBlank(pictureURI)) {
                    iv_picture.setImageURI(Uri.parse(pictureURI));
                }

            }
        }

        AndroidUtil.verifyPermissionsForScanner(this);
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
                CharSequence[] items = new CharSequence[]{"Camera", "Gallerie"};
                adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface d, int n) {
                        switch (n) {
                            case 0:
                                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                File photoFile = null;
                                try {
                                    photoFile = createImageFile();
                                } catch (IOException ex) {
                                    // Error occurred while creating the File
                                }
                                if (photoFile != null) {
                                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.example.projet_pfe_android.provider", photoFile);
                                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    imagepath = photoURI;
                                }
                                startActivityForResult(takePicture, AndroidUtil.IMAGE_CAPTURE_REQUEST_CODE);
                                break;
                            case 1:
                                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickPhoto, AndroidUtil.RESOURCE_PICK_REQUEST_CODE);
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

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageview = findViewById(R.id.iv_picture);
        switch (requestCode) {
            case AndroidUtil.IMAGE_CAPTURE_REQUEST_CODE:
                if (resultCode == RESULT_OK && data == null) {
                    Picasso.get().load(imagepath).into(imageview);
                }
                break;
            case AndroidUtil.RESOURCE_PICK_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    imageview.setImageURI(selectedImage);
                    imagepath = selectedImage;
                }
                break;
            case AndroidUtil.SCANNER_REQUEST_CODE: //REQUEST_CODE of IntentIntegrator of the zxing library
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (result != null) {
                    if (result.getContents() == null) {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        EditText et_numS = findViewById(R.id.NumS1);
                        et_numS.setText(result.getContents());
                    }
                } else {
                    // This is important, otherwise the result will not be passed to the fragment
                    super.onActivityResult(requestCode, resultCode, data);
                }
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_product_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (productId == JavaUtil.NO_RESULT) {
            MenuItem menuItem = menu.findItem(R.id.delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.valider):
                if (ProductValidator.validateInputForAdding(this)) {
                    saveProduct();
                    finish();
                } else {
                    Toast.makeText(this, "Entrée Invalide", Toast.LENGTH_SHORT).show();
                }
                break;
            case (R.id.delete):
                break;
            case (android.R.id.home):
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveProduct() {

        if (product == null) {
            product = new Product();
        }

        product.setName(et_nom.getText().toString());///////
        product.setBrand(et_brand.getText().toString());
        product.setDescription(et_description.getText().toString());
        product.setSafetyStockQty(Float.parseFloat(et_Stock1.getText().toString()));///////
        product.setUOM(et_uom.getText().toString());///////
        product.setUnitPrice(Double.parseDouble(et_prix_achat.getText().toString()));///////
        product.setSalePrice(Double.valueOf(et_prix_vente.getText().toString()));///////
        if (imagepath != null) {
            product.setPictureURI(imagepath.toString());
        }
        product.setSerialNumber(et_numS.getText().toString());

        if (productId == JavaUtil.NO_RESULT) {
            viewModel.insertProduct(product);
        } else {
            viewModel.updateProduct(product);
        }
    }
}
