package com.svalero.storeapp.presenter;

import com.svalero.storeapp.contract.ProductListContract;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.model.ProductListModel;

import java.util.List;

public class ProductListPresenter implements ProductListContract.Presenter, ProductListContract.Model.OnLoadProductListener {
    private ProductListModel model;
    private ProductListContract.View view;

    public ProductListPresenter(ProductListContract.View view){
        this.view = view;
        this.model = new ProductListModel();
    }

    @Override
    public void loadAllProducts() {
        model.loadAllProducts(this);
    }

    @Override
    public void loadProductsByName(String name) {

    }

    @Override
    public void editProduct(long id, Product editedProduct) {

    }

    @Override
    public void deleteProduct(long id) {

    }

    @Override
    public void onLoadProductsSuccess(List<Product> productList) {
        view.showProducts(productList);
    }

    @Override
    public void onLoadProductsError(String message) {
        view.showMessage(message);
    }
}
