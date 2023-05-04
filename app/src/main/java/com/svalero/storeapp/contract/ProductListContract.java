package com.svalero.storeapp.contract;

import com.svalero.storeapp.domain.Product;

import java.util.List;

public interface ProductListContract {
    interface Model{
        interface OnLoadProductListener {
            void onLoadProductsSuccess(List<Product> productList);
            void onLoadProductsError(String message);
        }
        void loadAllProducts(OnLoadProductListener listener);
        List<Product> loadProductsByName(String name);
        Product editProduct(long id, Product editedProduct);
        boolean deleteProduct(long id);
    }

    interface View{
        void showProducts(List<Product> productList);
        void showMessage(String name);
    }

    interface Presenter {
        void loadAllProducts();
        void loadProductsByName(String name);
        void editProduct(long id, Product editedProduct);
        void deleteProduct(long id);
    }
}
