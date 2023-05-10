package com.svalero.storeapp.presenter.product;

import com.svalero.storeapp.contract.product.RegisterProductContract;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.model.product.RegisterProductModel;
import com.svalero.storeapp.view.RegisterProductView;

public class RegisterProductPresenter implements RegisterProductContract.Presenter, RegisterProductContract.Model.OnRegisterProductListener{

    private RegisterProductModel model;
    private RegisterProductView view;

    public RegisterProductPresenter(RegisterProductView view){
        model = new RegisterProductModel();
        this.view = view;
    }

    @Override
    public void registerProduct(Product product, String token) {
        model.registerProduct(token, product, this);
    }

    @Override
    public void onRegisterProductSuccess(Product product) {
        view.showMessage("El producto: " + product.getName() + " se ha añadido correctamente!");
    }

    @Override
    public void onRegisterProductError(String message) {
        view.showError(message);
    }
}
