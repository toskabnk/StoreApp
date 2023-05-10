package com.svalero.storeapp.model.inventory;

import com.svalero.storeapp.api.AmazonAAApiInterface;
import com.svalero.storeapp.api.AmazonAASecureApi;
import com.svalero.storeapp.contract.inventory.RegisterMapInventoryContract;
import com.svalero.storeapp.domain.Inventory;
import com.svalero.storeapp.domain.InventoryMapDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterMapInventoryModel implements RegisterMapInventoryContract.Model {
    @Override
    public void registerMap(String token, long inventoryID, InventoryMapDTO inventoryMapDTO, OnRegisterMapInventoryListener listener) {
        AmazonAAApiInterface api = AmazonAASecureApi.buildInstance(token);
        Call<Inventory> inventoryCall = api.addWaypoint(inventoryID, inventoryMapDTO);
        inventoryCall.enqueue(new Callback<Inventory>() {
            @Override
            public void onResponse(Call<Inventory> call, Response<Inventory> response) {
                listener.onRegisterMapInventorySuccess(response.body());
            }

            @Override
            public void onFailure(Call<Inventory> call, Throwable t) {
                t.printStackTrace();
                listener.onRegisterMapInventoryError("No se ha podido registrar la ubicacion");
            }
        });
    }
}
