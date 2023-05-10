package com.svalero.storeapp.view;

import static com.svalero.storeapp.util.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.storeapp.R;
import com.svalero.storeapp.contract.product.EditProductContract;
import com.svalero.storeapp.contract.product.RegisterProductContract;
import com.svalero.storeapp.db.StoreAppDatabase;
import com.svalero.storeapp.domain.PersistData;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.presenter.product.EditProductPresenter;
import com.svalero.storeapp.presenter.product.RegisterProductPresenter;

public class RegisterProductView extends AppCompatActivity implements RegisterProductContract.View, EditProductContract.View {

    private EditText etName;
    private EditText etDescription;
    private EditText etPrice;
    private EditText etCategory;
    private Button button;
    private RegisterProductPresenter registerProductPresenter;
    private EditProductPresenter editProductPresenter;
    private PersistData persistData;
    String username;
    Product productEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product_view);

        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        persistData = new PersistData(0, "", "", "",false, false ,false);        try{
            persistData = db.getPersistDataDAO().getPersistData();
        }   catch (SQLiteConstraintException sce) {
            Log.i("RegisterProductView" , "onCreate - Error");
            sce.printStackTrace();
        } finally {
            db.close();
        }

        etName = findViewById(R.id.registerProductName);
        etDescription = findViewById(R.id.registerProductDescription);
        etPrice = findViewById(R.id.registerProductPrice);
        etCategory = findViewById(R.id.registerProductCategory);
        button = findViewById(R.id.registerProductRegister);

        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");
        productEdit = (Product) intentFrom.getSerializableExtra("editProduct");
        Log.i("RegisterProductView" , "onCreate - Intent Username: " + username);
        Log.i("RegisterProductView", "onCreate - Intent Product: " + productEdit);

        if(productEdit != null){
            etName.setText(productEdit.getName());
            etCategory.setText(productEdit.getCategory());
            etDescription.setText(productEdit.getDescription());
            etPrice.setText(String.valueOf(productEdit.getPrice()));
            button.setText(R.string.editButton);
        }
        registerProductPresenter = new RegisterProductPresenter(this);
        editProductPresenter = new EditProductPresenter(this);
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
        String name = etName.getText().toString();
        String description = etDescription.getText().toString();
        String category = etCategory.getText().toString();
        float price = Float.parseFloat(etPrice.getText().toString());

        if(productEdit != null){
            Product product = new Product(productEdit.getId(), name, description, price, category);
            editProductPresenter.editProduct(product, persistData.getToken());
        } else {
            Product product = new Product(name, description, price, category);
            registerProductPresenter.registerProduct(product, persistData.getToken());
        }
    }
}