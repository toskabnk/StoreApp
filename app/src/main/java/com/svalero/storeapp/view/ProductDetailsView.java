package com.svalero.storeapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.storeapp.R;
import com.svalero.storeapp.adapter.ReviewAdapter;
import com.svalero.storeapp.contract.product.ProductDetailsContract;
import com.svalero.storeapp.contract.review.ReviewListContract;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.domain.Review;
import com.svalero.storeapp.presenter.product.ProductDetailsPresenter;
import com.svalero.storeapp.presenter.review.ReviewListPresenter;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsView extends AppCompatActivity implements ProductDetailsContract.View, ReviewListContract.View {

    private List<Review> reviewList;
    private ReviewAdapter adapter;
    private ReviewListPresenter reviewListPresenter;
    private ProductDetailsPresenter presenter;
    private Product product;
    private TextView tvName;
    private TextView tvDetails;
    private TextView tvPrice;
    private long productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_view);
        Intent intentFrom = getIntent();
        productId = intentFrom.getLongExtra("productId", 0L);
        if(productId == 0L){
            return;
        }

        presenter = new ProductDetailsPresenter(this);
        tvName = findViewById(R.id.tvProductDetailsName);
        tvDetails = findViewById(R.id.tvProductDetailsDescription);
        tvPrice = findViewById(R.id.tvProductDetailsPrice);
        presenter.loadProduct(productId);

        reviewListPresenter = new ReviewListPresenter(this);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        reviewList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rvReviews);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ReviewAdapter(this, reviewList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProduct(Product product) {
        tvName.setText(product.getName());
        tvDetails.setText(product.getDescription());
        tvPrice.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public void showReviews(List<Review> reviewList) {
        this.reviewList.clear();
        this.reviewList.addAll(reviewList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reviewListPresenter.loadAllReviewForProduct(productId);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}