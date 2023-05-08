package com.svalero.storeapp.model.inventory;

import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.api.AmazonAASecureApi;
import com.svalero.storeapp.contract.inventory.InventoryListContract;
import com.svalero.storeapp.domain.Inventory;
import com.svalero.storeapp.domain.Person;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryListModel implements InventoryListContract.Model {
    @Override
    public void loadAllInventories(String token, OnLoadInventoryListener listener) {
        AmazonAAApiInterface api = AmazonAASecureApi.buildInstance(token);
        Call<List<Inventory>> call = api.getAllInventories();
        call.enqueue(new Callback<List<Inventory>>() {
            @Override
            public void onResponse(Call<List<Inventory>> call, Response<List<Inventory>> response) {
                listener.onLoadInventoriesSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Inventory>> call, Throwable t) {
                t.printStackTrace();
                listener.onLoadInventoriesError("No se ham podido cargar los almacenes");
            }
        });
    }
}
