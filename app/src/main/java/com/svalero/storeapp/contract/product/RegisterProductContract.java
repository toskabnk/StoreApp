package com.svalero.storeapp.contract.product;

import com.svalero.storeapp.domain.Product;

public interface RegisterProductContract {
    interface Model{
        interface OnRegisterProductListener {
            void onRegisterProductSuccess(Product product);
            void onRegisterProductError(String message);
        }
        void registerProduct(String token, Product product, OnRegisterProductListener listener);
    }

    interface View {
        void showError(String error);
        void showMessage(String message);
    }

    interface Presenter {
        void registerProduct(Product product, String token);
    }
}
