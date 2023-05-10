package com.svalero.storeapp.view;

import static com.svalero.storeapp.util.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
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
    private List<Product> productListFull;
    private ProductAdapter adapter;
    private ProductListPresenter presenter;
    private PersistData persistData;
    private EditText etsearch;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLocationPermission();

        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");
        Log.i("MainActivity" , "onCreate - " + username);

        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        setUpPreferences(db);

        presenter = new ProductListPresenter(this);

        etsearch  = findViewById(R.id.etSearch);
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                productList.clear();
                productList.addAll(productListFull);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etsearch.getWindowToken(), 0);
                    etsearch.setText("");
                    return true;
                } else {
                    return false;
                }
            }
        });

        initializeRecyclerView(intentFrom);
    }

    private void setUpPreferences(StoreAppDatabase db) {
        try {
            persistData = db.getPersistDataDAO().getPersistData();
            if(persistData != null){
                Log.i("MainActivity" , "onCreate - Datos cargados!");
                //TODO: Autologin
            } else {
                persistData = new PersistData(0, "", "", "",false, false ,false);                db.getPersistDataDAO().insert(persistData);
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
        if(username != null){
            menu.findItem(R.id.userMenu).setVisible(true);
            menu.findItem(R.id.userMenu).setTitle(username);
            menu.findItem(R.id.menuLogin).setVisible(false);
            menu.findItem(R.id.menuLogout).setVisible(true);
            menu.findItem(R.id.menuAddProduct).setVisible(true);
            menu.findItem(R.id.menuAddReview).setVisible(false);
            menu.findItem(R.id.menuFavourite).setVisible(true);
            menu.findItem(R.id.menuInventories).setVisible(true);
            menu.findItem(R.id.menuPreferences).setVisible(true);

        } else {
            menu.findItem(R.id.menuLogout).setVisible(false);
            menu.findItem(R.id.menuAddProduct).setVisible(false);
            menu.findItem(R.id.menuAddReview).setVisible(false);
            menu.findItem(R.id.menuFavourite).setVisible(false);
            menu.findItem(R.id.menuInventories).setVisible(false);
            menu.findItem(R.id.menuPreferences).setVisible(false);


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
        } else if(item.getItemId() == R.id.menuFavourite){
            Intent intent = new Intent(this, FavouritesView.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menuInventories){
            Intent intent = new Intent(this, InventoryListView.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }  else if(item.getItemId() == R.id.menuPreferences){
            Intent intent = new Intent(this, Preferences.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        return false;
    }

    private void initializeRecyclerView(Intent intentFrom){
        productList = new ArrayList<>();
        productListFull = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rvListProducts);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(this, productList, intentFrom, persistData.getToken());
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
        productListFull.addAll(productList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String name) {
        Toast.makeText(this, name, Toast.LENGTH_LONG).show();
    }

    private void checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            }
        }

    }
}