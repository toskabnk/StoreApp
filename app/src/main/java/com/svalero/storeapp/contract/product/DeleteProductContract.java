package com.svalero.storeapp.contract.product;

public interface DeleteProductContract {
    interface Model {
        interface OnDeleteProductListener {
            void onDeleteProductSuccess(String message);
            void onDeleteProductError(String error);
        }
        void deleteProduct(String token, long id, OnDeleteProductListener listener);
    }

    interface View {
        void showError(String message);
        void showMessage(String message);
    }

    interface Presenter {
        void deleteProduct(long id, String token);
    }
}
