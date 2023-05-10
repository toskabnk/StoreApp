package com.svalero.storeapp.presenter.product;

import com.svalero.storeapp.adapter.ProductAdapter;
import com.svalero.storeapp.adapter.ReviewAdapter;
import com.svalero.storeapp.contract.product.DeleteProductContract;
import com.svalero.storeapp.model.product.DeleteProductModel;

public class DeleteProductPresenter implements DeleteProductContract.Presenter, DeleteProductContract.Model.OnDeleteProductListener {

    private DeleteProductModel model;
    private ProductAdapter view;

    public DeleteProductPresenter(ProductAdapter view){
        model = new DeleteProductModel();
        this.view = view;
    }
    @Override
    public void onDeleteProductSuccess(String message) {
        view.showMessage(message);
    }

    @Override
    public void onDeleteProductError(String error) {
        view.showError(error);
    }

    @Override
    public void deleteProduct(long id, String token) {
        model.deleteProduct(token, id, this);
    }
}
