package com.example.projet_pfe_android;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;

import com.example.projet_pfe_android.Data.Database;
import com.example.projet_pfe_android.Data.FournisseurDao;
import com.example.projet_pfe_android.Data.ProductDao;
import com.example.projet_pfe_android.Data.TransactionDao;
import com.example.projet_pfe_android.Model.Fournisseur;
import com.example.projet_pfe_android.Model.Product;
import com.example.projet_pfe_android.Model.Transaction;
import com.example.projet_pfe_android.Model.TransactionLine;

import java.util.List;
import java.util.concurrent.ExecutionException;

/***
 * Dans cette classe on va implémenter tous les accès à la base de donnée
 **/
public class Repository {

    private Database database;
    private ProductDao productDao;
    private FournisseurDao fournisseurDao;
    private TransactionDao transactionDao;
    public Repository(Application application) {

//        Connexion à la BDD
        database = Database.getInstance(application); //instance de la BDD
        productDao = database.productDao();
        fournisseurDao= database.fournisseurDao();
        transactionDao=database.transactionDao();
    }


    public void updateProduct(Product product){
        new UpdateProductAsync(productDao).execute(product);
    }
    // fournisseur update

    public void updateFournisseur(Fournisseur fournisseur){
        new UpdateFournisseurAsync(fournisseurDao).execute(fournisseur);
    }

    public LiveData<List<Product>> getAllProducts(){
        return productDao.getAllProducts();
    }

    // fournisseur
    public LiveData<List<Fournisseur>> getAllFournisseurs() { return fournisseurDao.getAllFournisseurs();}


    public void insertProduct(Product product){
        new InsertProductAsync(productDao).execute(product);
    }

    // fournisseur

    public void insertFournisseur (Fournisseur fournisseur){
        new InsertFournisseurAsync(fournisseurDao).execute(fournisseur);
    }


    public void deleteAllProducts(){
        new DeleteAllProductsAsync(productDao).execute();
    }

    //fournisseur
    public  void deleteAllFournisseurs(){ new DeleteAllFournisseursAsync(fournisseurDao).execute();}



    public void deleteProduct(Product product) {
        new DeleteProductAsync(productDao).execute(product);
    }
// fournisseur
    public void deleteFournisseur(Fournisseur fournisseur){
        new  DeleteFournisseurAsync(fournisseurDao).execute(fournisseur);
    }

    public LiveData<List<Product>> getCurrentTransactionProducts() {
        return productDao.getCurrentTransactionProducts();
    }

    public void updateProducts(List<Product> products) {
        new UpdateProductsAsync(productDao).execute(products);
    }

    public double getStockValue() throws ExecutionException, InterruptedException {
        return new GetStockValueAsync(productDao).execute().get();
    }

    public Product getProductById(int productId) throws ExecutionException, InterruptedException {
        return new GetProductByIdAsync(productDao).execute(productId).get();
    }

    public Fournisseur getFournisseurById(int fournisseurId) throws ExecutionException, InterruptedException {
        return new GetFournisseurByIdAsync(fournisseurDao).execute(fournisseurId).get();
    }

    public void commitTransactionLines(List<TransactionLine> transactionLines){
        new CommitTransactionLinesAsync(productDao).execute(transactionLines);
    }

    public int insertTransaction(Transaction transaction) throws ExecutionException, InterruptedException {
        return new InsertTransactionAsync(transactionDao).execute(transaction).get();
    }

    public void insertTransactionLines(List<TransactionLine> transactionLines) {
        new InsertTransactionLinesAsync(transactionDao).execute(transactionLines);
    }

    public LiveData<List<TransactionLine>> getAllTransactionLines() {
        return transactionDao.getAllTransactionLines();
    }

//    Async Tasks : DB Transactions
//    -------------------------------------------------------------------------------------------------------------------------

    private class InsertTransactionLinesAsync extends AsyncTask<List<TransactionLine>,Void,Void>{
        private TransactionDao transactionDao;

        public InsertTransactionLinesAsync(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(List<TransactionLine>... lists) {
            for (TransactionLine t : lists[0])
                transactionDao.insertTransactionLine(t);
            return null;
        }
    }
    private class InsertTransactionAsync extends AsyncTask<Transaction,Void,Integer>{
        private TransactionDao transactionDao;

        public InsertTransactionAsync(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Integer doInBackground(Transaction... transactions) {
            return (int) transactionDao.insertTransaction(transactions[0]);
        }
    }

    private class CommitTransactionLinesAsync extends AsyncTask<List<TransactionLine>,Void,Void>{
        private ProductDao productDao;

        public CommitTransactionLinesAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(List<TransactionLine>... lists) {
            for (TransactionLine t : lists[0])
                productDao.commitTransactionQty(t.getQuantity(),t.getProductId());
            return null;
        }
    }
    private class GetFournisseurByIdAsync extends AsyncTask<Integer,Void,Fournisseur>{
        private FournisseurDao fournisseurDao;

        public GetFournisseurByIdAsync(FournisseurDao fournisseurDao) {
            this.fournisseurDao =fournisseurDao;
        }

        @Override
        protected Fournisseur doInBackground(Integer... integers) {
            return fournisseurDao.getFournisseurById(integers[0]);
        }
    }

    private class GetProductByIdAsync extends AsyncTask<Integer,Void,Product>{
        private ProductDao productDao;

        public GetProductByIdAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Product doInBackground(Integer... integers) {
            return productDao.getProductById(integers[0]);
        }
    }

    private class GetStockValueAsync extends AsyncTask<Void,Void,Double>{
        private ProductDao productDao;

        public GetStockValueAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Double doInBackground(Void... voids) {
            return productDao.getStockValue();
        }
    }

    private class UpdateProductsAsync extends AsyncTask<List<Product>,Void,Void>{
        private ProductDao productDao;

        public UpdateProductsAsync(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(List<Product>... lists) {
            productDao.updateProducts(lists[0]);
            return null;
        }
    }


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

    // async tasks fournisseur


    private  class DeleteFournisseurAsync extends AsyncTask<Fournisseur,Void,Void>{
        private FournisseurDao fournisseurDao;

        public DeleteFournisseurAsync(FournisseurDao fournisseurDao) {
            this.fournisseurDao = fournisseurDao;
        }

        @Override
        protected Void doInBackground(Fournisseur... fournisseurs) {
            fournisseurDao.deleteFournisseur(fournisseurs[0]);
            return null;
        }
    }

    private class UpdateFournisseurAsync extends AsyncTask<Fournisseur,Void,Void>{
        private FournisseurDao fournisseurDao;

        public UpdateFournisseurAsync(FournisseurDao fournisseurDao) {
            this.fournisseurDao = fournisseurDao;
        }

        @Override
        protected Void doInBackground(Fournisseur... fournisseurs) {
            fournisseurDao.updateFournisseur(fournisseurs[0]);
            return null;
        }
    }

    private class InsertFournisseurAsync extends AsyncTask<Fournisseur,Void,Void>{
        private FournisseurDao fournisseurDao;

        public InsertFournisseurAsync(FournisseurDao fournisseurDao) {
            this.fournisseurDao = fournisseurDao;
        }

        @Override
        protected Void doInBackground(Fournisseur... fournisseurs) {
            fournisseurDao.insertFournisseur(fournisseurs[0]);
            return null;
        }
    }

    private class DeleteAllFournisseursAsync extends AsyncTask<Void,Void,Void>{
        private FournisseurDao fournisseurDao;

        public DeleteAllFournisseursAsync(FournisseurDao fournisseurDao) {
            this.fournisseurDao = fournisseurDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fournisseurDao.deleteAllFournisseurs();
            return null;
        }
    }
}
