package com.svalero.storeapp.model.review;

import android.util.Log;

import com.svalero.storeapp.api.AmazonAAApi;
import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.contract.review.ReviewListContract;
import com.svalero.storeapp.domain.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewListModel implements ReviewListContract.Model {
    @Override
    public void loadAllReviewsForProduct(long id, OnLoadReviewListener listener) {
        AmazonAAApiInterface apiInterface = AmazonAAApi.buildInstance();
        Call<List<Review>> callReview = apiInterface.getReviewsByProduct(id);
        Log.d("reviews", "Llamada desde model");
        callReview.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                Log.d("reviews", "Llamada desde model ok");
                List<Review> reviewList = response.body();
                listener.onLoadReviewSuccess(reviewList);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.d("reviews", "Llamada desde model error");
                Log.d("reviews", t.getMessage());
                t.printStackTrace();
                String message = "Error al conseguir las revies del producto";
                listener.onLoadReviewError(t.getMessage());
            }
        });
    }
}
