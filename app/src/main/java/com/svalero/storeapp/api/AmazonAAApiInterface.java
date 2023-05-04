package com.svalero.storeapp.api;

import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.domain.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AmazonAAApiInterface {
    //Products
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


    //Reviews
    @GET("reviews")
    Call<List<Review>> getReviews();

    @GET("reviews/{id}")
    Call<Review> getReview(@Path("id") long id);

    @GET("reviews/")
    Call<List<Review>> getReviewsByProduct(@Query("productId") long id);

    @POST("reviews")
    Call<Review> addReview(@Body Review review);

    @PUT("reviews/{id}")
    Call<Review> editReview(@Path("id")long id, @Body Review review);

    @DELETE("reviews/{id}")
    Call<Void> deleteReview(@Path("id") long id);
}
