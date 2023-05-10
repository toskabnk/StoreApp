package com.svalero.storeapp.contract.product;

import com.svalero.storeapp.domain.Product;

public interface ProductDetailsContract {
    interface Model {
        interface OnLoadProductListener{
            void onLoadProductSuccess(Product product);
            void onLoadProductError(String message);
        }
        void loadProduct(long id, OnLoadProductListener listener);
    }

    interface View {
        void showProduct(Product product);
        void showMessage(String message);
    }

    interface Presenter {
        void loadProduct(long id);
    }
}
