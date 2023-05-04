package com.svalero.storeapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.storeapp.R;
import com.svalero.storeapp.contract.product.ProductDetailsContract;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.presenter.product.ProductDetailsPresenter;

public class ProductDetailsView extends AppCompatActivity implements ProductDetailsContract.View {

    private ProductDetailsPresenter presenter;
    private Product product;
    private TextView tvName;
    private TextView tvDetails;
    private TextView tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_view);
        Intent intentFrom = getIntent();
        long productId = intentFrom.getLongExtra("productId", 0L);
        if(productId == 0L){
            return;
        }

        presenter = new ProductDetailsPresenter(this);
        tvName = findViewById(R.id.tvProductDetailsName);
        tvDetails = findViewById(R.id.tvProductDetailsDescription);
        tvPrice = findViewById(R.id.tvProductDetailsPrice);
        presenter.loadProduct(productId);
    }

    @Override
    public void showProduct(Product product) {
        tvName.setText(product.getName());
        tvDetails.setText(product.getDescription());
        tvPrice.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}