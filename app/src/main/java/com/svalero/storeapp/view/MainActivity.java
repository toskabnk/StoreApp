package com.svalero.storeapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.svalero.storeapp.R;
import com.svalero.storeapp.adapter.ProductAdapter;
import com.svalero.storeapp.contract.product.ProductListContract;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.presenter.product.ProductListPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ProductListContract.View {
    private List<Product> productList;
    private ProductAdapter adapter;
    private ProductListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new ProductListPresenter(this);
        initializeRecyclerView();
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