package com.svalero.storeapp.model.review;

import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.api.AmazonAASecureApi;
import com.svalero.storeapp.contract.review.DeleteReviewContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteReviewModel implements DeleteReviewContract.Model {
    @Override
    public void deleteReview(String token, long id, OnDeleteReviewListener listener) {
        AmazonAAApiInterface api = AmazonAASecureApi.buildInstance(token);
        Call<Void> call = api.deleteReview(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 204){
                    listener.onDeleteReviewSuccess("Review eliminada correctamente");
                } else {
                    listener.onDeleteReviewError("Ha habido un error al borrar la review");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                listener.onDeleteReviewError("Ha habido un error al borrar la review");
            }
        });
    }
}
