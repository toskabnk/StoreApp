package com.svalero.storeapp.api;

import com.svalero.storeapp.domain.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductsApiInterface {

    @GET("products")
    Call<List<Product>> getProducts();

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") long id);

    @POST("products")
    Call<Product> addProduct(@Body Product product);

    @PUT("products/{id}")
    Call<Product> editProduct(@Path("id")long id, @Body Product product);

    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") long id);
}
