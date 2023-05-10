package com.svalero.storeapp.presenter.product;

import com.svalero.storeapp.contract.product.EditProductContract;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.model.product.EditProductModel;
import com.svalero.storeapp.view.RegisterProductView;

public class EditProductPresenter implements EditProductContract.Presenter, EditProductContract.Model.OnEditProductListener {
    private EditProductModel model;
    private RegisterProductView view;

    public EditProductPresenter(RegisterProductView view){
        model = new EditProductModel();
        this.view = view;
    }

    @Override
    public void editProduct(Product product, String token) {
        model.editProduct(token, product, this);
    }

    @Override
    public void onEditProductSuccess(Product product) {
        view.showMessage("El producto: " + product.getName() + " se ha editado correctamente!");
    }

    @Override
    public void onEditProductError(String message) {
        view.showError(message);
    }
}
