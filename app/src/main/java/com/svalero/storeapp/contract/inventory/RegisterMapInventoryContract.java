package com.svalero.storeapp.contract.inventory;

import com.svalero.storeapp.domain.Inventory;
import com.svalero.storeapp.domain.InventoryMapDTO;

public interface RegisterMapInventoryContract {
    interface Model {
        interface OnRegisterMapInventoryListener{
            void onRegisterMapInventorySuccess(Inventory inventory);
            void onRegisterMapInventoryError(String error);
        }
        void registerMap(String token, long inventoryID, InventoryMapDTO inventoryMapDTO, OnRegisterMapInventoryListener listener);
    }

    interface View {
        void showError(String error);
        void showInventory(Inventory inventory);
    }

    interface Presenter {
        void registerMap(String toke, long inventoryID, InventoryMapDTO inventoryMapDTO);
    }
}
