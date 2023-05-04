package com.svalero.storeapp.model.product;

import android.util.Log;

import com.svalero.storeapp.api.AmazonAAApi;
import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.contract.product.ProductDetailsContract;
import com.svalero.storeapp.domain.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsModel implements ProductDetailsContract.Model {

    @Override
    public void loadProduct(long id, OnLoadProductListener listener) {
        AmazonAAApiInterface apiInterface = AmazonAAApi.buildInstance();
        Call<Product> callProduct = apiInterface.getProduct(id);
        Log.d("product", "Llamada desde model");
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Log.d("product", "Llamada desde model ok");
                Product product = response.body();
                listener.onLoadProductSuccess(product);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("product", "Llamada desde model error");
                Log.d("product", t.getMessage());
                t.printStackTrace();
                String message = "Error al conseguir el producto";
                listener.onLoadProductError(t.getMessage());
            }
        });
    }
}
