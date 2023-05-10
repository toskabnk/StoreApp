package com.svalero.storeapp.presenter.product;

import com.svalero.storeapp.contract.product.ProductListContract;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.model.product.ProductListModel;

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
    public void onLoadProductsSuccess(List<Product> productList) {
        view.showProducts(productList);
    }

    @Override
    public void onLoadProductsError(String message) {
        view.showMessage(message);
    }
}
