package com.svalero.storeapp.model.product;

import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.api.AmazonAASecureApi;
import com.svalero.storeapp.contract.product.DeleteProductContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteProductModel implements DeleteProductContract.Model {
    @Override
    public void deleteProduct(String token, long id, OnDeleteProductListener listener) {
        AmazonAAApiInterface api = AmazonAASecureApi.buildInstance(token);
        Call<Void> call = api.deleteProduct(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 418){
                    listener.onDeleteProductError("Error al borrar el producto");
                } else {
                    listener.onDeleteProductSuccess("Producto eliminado correctamente!");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                listener.onDeleteProductError("Error al borrar el producto");
            }
        });
    }
}
