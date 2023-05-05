package com.svalero.storeapp.contract.product;

import com.svalero.storeapp.domain.Product;

public interface EditProductContract {
    interface Model {
        interface OnEditProductListener {
            void onEditProductSuccess(Product product);
            void onEditProductError(String message);
        }
        void editProduct(String token, Product product, OnEditProductListener listener);
    }
    interface View {
        void showError(String message);
        void showMessage(String message);
    }

    interface Presenter {
        void editProduct(Product product, String token);
    }

}
