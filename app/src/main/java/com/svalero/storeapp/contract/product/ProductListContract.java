package com.svalero.storeapp.contract.product;

import com.svalero.storeapp.domain.Product;

import java.util.List;

public interface ProductListContract {
    interface Model{
        interface OnLoadProductListener {
            void onLoadProductsSuccess(List<Product> productList);
            void onLoadProductsError(String message);
        }
        void loadAllProducts(OnLoadProductListener listener);
    }

    interface View{
        void showProducts(List<Product> productList);
        void showMessage(String name);
    }

    interface Presenter {
        void loadAllProducts();
    }
}
