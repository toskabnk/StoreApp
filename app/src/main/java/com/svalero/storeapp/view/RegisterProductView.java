package com.svalero.storeapp.view;

import static com.svalero.storeapp.util.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.storeapp.R;
import com.svalero.storeapp.contract.product.RegisterProductContract;
import com.svalero.storeapp.db.StoreAppDatabase;
import com.svalero.storeapp.domain.PersistData;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.presenter.product.RegisterProductPresenter;

public class RegisterProductView extends AppCompatActivity implements RegisterProductContract.View {

    private RegisterProductPresenter presenter;
    private PersistData persistData;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product_view);

        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        persistData = new PersistData(0, "", "", "",false);
        try{
            persistData = db.getPersistDataDAO().getPersistData();
        }   catch (SQLiteConstraintException sce) {
            Log.i("RegisterProductView" , "onCreate - Error");
            sce.printStackTrace();
        } finally {
            db.close();
        }

        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");
        Log.i("RegisterProductView" , "onCreate - " + username);
        if(username == null){
            username = "";
        }

        presenter = new RegisterProductPresenter(this);
    }



    @Override
    public void showError(String error) {
        Snackbar.make(((EditText) findViewById(R.id.registerProductName)), error,
                BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(((EditText) findViewById(R.id.registerProductName)), message,
                BaseTransientBottomBar.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void register(View view){
        EditText etName = findViewById(R.id.registerProductName);
        EditText etDescription = findViewById(R.id.registerProductDescription);
        EditText etPrice = findViewById(R.id.registerProductPrice);
        EditText etCategory = findViewById(R.id.registerProductCategory);

        String name = etName.getText().toString();
        String description = etDescription.getText().toString();
        String category = etCategory.getText().toString();
        float price = Float.parseFloat(etPrice.getText().toString());

        Product product = new Product(name, description, price, category);
        presenter.registerProduct(product, persistData.getToken());
    }
}