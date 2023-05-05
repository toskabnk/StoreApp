package com.svalero.storeapp.view;

import static com.svalero.storeapp.util.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.svalero.storeapp.R;
import com.svalero.storeapp.adapter.ProductAdapter;
import com.svalero.storeapp.contract.product.ProductListContract;
import com.svalero.storeapp.db.StoreAppDatabase;
import com.svalero.storeapp.domain.PersistData;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.presenter.product.ProductListPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListContract.View {
    private List<Product> productList;
    private ProductAdapter adapter;
    private ProductListPresenter presenter;
    private PersistData persistData;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");
        Log.i("MainActivity" , "onCreate - " + username);
        if(username == null){
            username = "";
        }

        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        setUpPreferences(db);

        presenter = new ProductListPresenter(this);
        initializeRecyclerView();
    }

    private void setUpPreferences(StoreAppDatabase db) {
        try {
            persistData = db.getPersistDataDAO().getPersistData();
            if(persistData != null){
                Log.i("MainActivity" , "onCreate - Datos cargados!");
                //TODO: Autologin
            } else {
                persistData = new PersistData(0,"","","", false);
                db.getPersistDataDAO().insert(persistData);
                Log.i("MainActivity" , "onCreate - Datos creados!");
            }
        }  catch (SQLiteConstraintException sce) {
            Log.i("MainActivity" , "onCreate - Error");
        } finally {
            db.close();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.user_menu, menu);
        if(!username.equals("")){
            menu.findItem(R.id.userMenu).setVisible(true);
            menu.findItem(R.id.userMenu).setTitle(username);
            menu.findItem(R.id.menuLogin).setVisible(false);
            menu.findItem(R.id.menuLogout).setVisible(true);
            menu.findItem(R.id.menuAddProduct).setVisible(true);
        } else {
            menu.findItem(R.id.menuLogout).setVisible(false);
            menu.findItem(R.id.menuAddProduct).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuLogin){
            Intent intent = new Intent(this, LoginView.class);
            startActivity(intent);
            return true;
        } else if(item.getItemId() == R.id.menuAddProduct){
            Intent intent = new Intent(this, RegisterProductView.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menuLogout){
            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
            deleteDialog.setMessage(R.string.confirmationMessage).setTitle(R.string.logoutMessage)
                    .setPositiveButton(R.string.confirmationYes, (dialog, id) -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }).setNegativeButton(R.string.confirmationNo, (dialog, id) -> {
                        dialog.dismiss();
                    });
            AlertDialog dialog = deleteDialog.create();
            dialog.show();
        }
        return false;
    }

    private void initializeRecyclerView(){
        productList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rvListProducts);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);
    }

    protected void onResume(){
        super.onResume();
        presenter.loadAllProducts();
    }

    @Override
    public void showProducts(List<Product> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String name) {
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
    }
}