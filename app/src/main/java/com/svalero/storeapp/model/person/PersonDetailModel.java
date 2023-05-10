package com.svalero.storeapp.model.person;

import com.svalero.storeapp.api.AmazonAAApi;
import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.api.AmazonAASecureApi;
import com.svalero.storeapp.contract.person.PersonDetailContract;
import com.svalero.storeapp.domain.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailModel implements PersonDetailContract.Model {
    @Override
    public void getPersonDetail(String username, String token,  OnPersonDatailListener listener) {
        AmazonAAApiInterface api = AmazonAASecureApi.buildInstance(token);
        Call<List<Person>> personCall = api.getPersonByUsername(username);
        personCall.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if(response.body().isEmpty()){
                    listener.onPersonDetailError("Error al conseguir el usuario");
                } else {
                    listener.onPersonDetailSuccess(response.body().get(0));
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                t.printStackTrace();
                listener.onPersonDetailError("Error al conseguir el usuario");
            }
        });
    }
}
