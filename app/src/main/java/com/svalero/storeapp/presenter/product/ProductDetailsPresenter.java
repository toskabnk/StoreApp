package com.svalero.storeapp.presenter.product;

import com.svalero.storeapp.contract.product.ProductDetailsContract;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.model.product.ProductDetailsModel;

public class ProductDetailsPresenter implements ProductDetailsContract.Presenter, ProductDetailsContract.Model.OnLoadProductListener {

    private ProductDetailsModel model;
    private ProductDetailsContract.View view;
    private long id;

    public ProductDetailsPresenter(ProductDetailsContract.View view){
        this.view = view;
        this.model = new ProductDetailsModel();
    }

    @Override
    public void loadProduct(long id) {
        model.loadProduct(id, this);
    }

    @Override
    public void onLoadProductSuccess(Product product) {
        view.showProduct(product);
    }

    @Override
    public void onLoadProductError(String message) {
        view.showMessage(message);
    }
}
