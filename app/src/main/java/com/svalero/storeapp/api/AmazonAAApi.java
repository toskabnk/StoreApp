package com.svalero.storeapp.api;

import static com.svalero.storeapp.util.Constants.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AmazonAAApi {
    public static AmazonAAApiInterface buildInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(AmazonAAApiInterface.class);
    }
}
