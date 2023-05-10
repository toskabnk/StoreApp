package com.svalero.storeapp.model.review;

import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.api.AmazonAASecureApi;
import com.svalero.storeapp.contract.review.RegisterReviewContract;
import com.svalero.storeapp.domain.Review;
import com.svalero.storeapp.domain.ReviewDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterReviewModel implements RegisterReviewContract.Model {
    @Override
    public void registerReview(String token, ReviewDTO review, OnRegisterReviewListener listener) {
        AmazonAAApiInterface api = AmazonAASecureApi.buildInstance(token);
        Call<Review> reviewCall = api.addReview(review);
        reviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Review review1 = response.body();
                listener.onRegisterReviewSuccess(review1);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                t.printStackTrace();
                listener.onRegisterReviewError("Error al añadir la review");
            }
        });
    }
}
