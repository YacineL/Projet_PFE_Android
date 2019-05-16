package com.example.projet_pfe_android;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.projet_pfe_android.Data.Database;
import com.example.projet_pfe_android.Data.ProductDao;
import com.example.projet_pfe_android.Model.Product;

import java.util.List;

/***
 * Dans cette classe on va implémenter tous les accès à la base de donnée
 **/
public class Repository {

    private Database database;
    private ProductDao productDao;

    public Repository(Application application) {

//        Connexion à la BDD
        database = Database.getInstance(application); //instance de la BDD
        productDao = database.productDao();
    }

    public void updateProduct(Product product){
        new UpdateProductAsync(productDao).execute(product);
    }

    public LiveData<List<Product>> getAllProducts(){
        return productDao.getAllProducts();
    }

    public void insertProduct(Product product){
        new InsertProductAsync(productDao).execute(product);
    }

    public void deleteAllProducts(){
        new DeleteAllProductsAsync(productDao).execute();
    }

    public void deleteProduct(Product product) {
        new DeleteProductAsync(productDao).execute(product);
    }


//    Async Tasks : DB Transactions

    private class DeleteProductAsync extends AsyncTask<Product,Void,Void>{
        private ProductDao productDao;

        public DeleteProductAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.deleteProduct(products[0]);
            return null;
        }
    }

    private class UpdateProductAsync extends AsyncTask<Product,Void,Void>{
        private ProductDao productDao;

        public UpdateProductAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.updateProduct(products[0]);
            return null;
        }
    }

    private class InsertProductAsync extends AsyncTask<Product,Void,Void>{
        private ProductDao productDao;

        public InsertProductAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insertProduct(products[0]);
            return null;
        }
    }

    private class DeleteAllProductsAsync extends AsyncTask<Void,Void,Void>{
        private ProductDao productDao;

        public DeleteAllProductsAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.deleteAllProducts();
            return null;
        }
    }
}
