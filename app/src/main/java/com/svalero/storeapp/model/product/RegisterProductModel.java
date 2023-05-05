package com.svalero.storeapp.model.product;

import android.content.Context;

import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.api.AmazonAASecureApi;
import com.svalero.storeapp.contract.product.RegisterProductContract;
import com.svalero.storeapp.domain.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterProductModel implements RegisterProductContract.Model {
    @Override
    public void registerProduct(String token, Product product, OnRegisterProductListener listener) {
        AmazonAAApiInterface api = AmazonAASecureApi.buildInstance(token);
        Call<Product> productCall = api.addProduct(product);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product1 = response.body();
                listener.onRegisterProductSuccess(product1);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
                String message = "Error al crear producto";
                listener.onRegisterProductError(message);
            }
        });

    }
}
