package com.svalero.storeapp.model;

import android.util.Log;

import com.svalero.storeapp.api.ProductsApi;
import com.svalero.storeapp.api.ProductsApiInterface;
import com.svalero.storeapp.contract.ProductListContract;
import com.svalero.storeapp.domain.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListModel implements ProductListContract.Model {
    @Override
    public void loadAllProducts(OnLoadProductListener listener) {
        ProductsApiInterface apiInterface = ProductsApi.buildInstance();
        Call<List<Product>> callProducts = apiInterface.getProducts();
        Log.d("products", "Llamada desde model");
        callProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d("products", "Llamada desde model ok");
                List<Product> productList = response.body();
                listener.onLoadProductsSuccess(productList);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("products", "Llamada desde model error");
                Log.d("products", t.getMessage());
                t.printStackTrace();
                String message = "Error al conseguir todos los productos";
                listener.onLoadProductsError(t.getMessage());
            }
        });
    }

    @Override
    public List<Product> loadProductsByName(String name) {
        return null;
    }

    @Override
    public Product editProduct(long id, Product editedProduct) {
        return null;
    }

    @Override
    public boolean deleteProduct(long id) {
        return false;
    }
}
