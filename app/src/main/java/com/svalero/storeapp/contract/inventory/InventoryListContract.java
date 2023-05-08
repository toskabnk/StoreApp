package com.svalero.storeapp.contract.inventory;

import com.svalero.storeapp.domain.Inventory;

import java.util.List;

public interface InventoryListContract {
    interface Model {
        interface OnLoadInventoryListener {
            void onLoadInventoriesSuccess(List<Inventory> inventoryList);
            void onLoadInventoriesError(String error);
        }
        void loadAllInventories(String token, OnLoadInventoryListener listener);
    }
     interface View {
        void showInventories(List<Inventory> inventoryList);
        void showError(String message);
     }

     interface Presenter {
        void loadAllInventories(String token);
     }
}
