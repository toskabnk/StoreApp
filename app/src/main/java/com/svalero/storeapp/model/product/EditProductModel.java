package com.svalero.storeapp.model.product;

import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.api.AmazonAASecureApi;
import com.svalero.storeapp.contract.product.EditProductContract;
import com.svalero.storeapp.domain.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductModel implements EditProductContract.Model {
    @Override
    public void editProduct(String token, Product product, OnEditProductListener listener) {
        AmazonAAApiInterface api = AmazonAASecureApi.buildInstance(token);
        Call<Product> productCall = api.editProduct(product.getId(),  product);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                listener.onEditProductSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
                String message = "No se ha podido editar el producto";
                listener.onEditProductError(message);
            }
        });
    }
}
