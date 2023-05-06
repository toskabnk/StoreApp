package com.svalero.storeapp.model.review;

import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.api.AmazonAASecureApi;
import com.svalero.storeapp.contract.review.EditReviewContract;
import com.svalero.storeapp.domain.Review;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditReviewModel implements EditReviewContract.Model {
    @Override
    public void editReview(String token, Review review, OnEditReviewListener listener) {
        AmazonAAApiInterface api = AmazonAASecureApi.buildInstance(token);
        Call<Review> reviewCall = api.editReview(review.getId(), review);
        reviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if(response.code() == 200){
                    listener.onEditReviewSuccess("Review editada correctamente");
                } else {
                    listener.onEditReviewError("Error al editar el comentario");
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                t.printStackTrace();
                listener.onEditReviewError("Error al editar el comentario");
            }
        });
    }
}
