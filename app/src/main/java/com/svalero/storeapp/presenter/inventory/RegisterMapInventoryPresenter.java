package com.svalero.storeapp.presenter.inventory;

import com.svalero.storeapp.contract.inventory.RegisterMapInventoryContract;
import com.svalero.storeapp.domain.Inventory;
import com.svalero.storeapp.domain.InventoryMapDTO;
import com.svalero.storeapp.model.inventory.RegisterMapInventoryModel;
import com.svalero.storeapp.view.SelectMapView;

public class RegisterMapInventoryPresenter implements RegisterMapInventoryContract.Presenter, RegisterMapInventoryContract.Model.OnRegisterMapInventoryListener {
    private RegisterMapInventoryModel model;
    private SelectMapView view;

    public RegisterMapInventoryPresenter(SelectMapView view){
        this.view = view;
        model = new RegisterMapInventoryModel();
    }
    @Override
    public void onRegisterMapInventorySuccess(Inventory inventory) {
        view.showInventory(inventory);
    }

    @Override
    public void onRegisterMapInventoryError(String error) {
        view.showError(error);
    }

    @Override
    public void registerMap(String toke, long inventoryID, InventoryMapDTO inventoryMapDTO) {
        model.registerMap(toke, inventoryID, inventoryMapDTO, this);
    }
}
