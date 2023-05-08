package com.svalero.storeapp.presenter.inventory;

import com.svalero.storeapp.contract.inventory.InventoryListContract;
import com.svalero.storeapp.domain.Inventory;
import com.svalero.storeapp.model.inventory.InventoryListModel;
import com.svalero.storeapp.view.InventoryListView;

import java.util.List;

public class InventoryListPresenter implements InventoryListContract.Presenter, InventoryListContract.Model.OnLoadInventoryListener {
    private InventoryListModel model;
    private InventoryListView view;

    public InventoryListPresenter(InventoryListView view){
        this.view = view;
        model = new InventoryListModel();
    }
    @Override
    public void onLoadInventoriesSuccess(List<Inventory> inventoryList) {
        view.showInventories(inventoryList);
    }

    @Override
    public void onLoadInventoriesError(String error) {
        view.showError(error);
    }

    @Override
    public void loadAllInventories(String token) {
        model.loadAllInventories(token, this);
    }
}
