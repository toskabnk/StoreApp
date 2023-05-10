package com.svalero.storeapp.presenter.review;

import com.svalero.storeapp.contract.review.EditReviewContract;
import com.svalero.storeapp.domain.Review;
import com.svalero.storeapp.model.review.EditReviewModel;
import com.svalero.storeapp.view.RegisterReviewView;

public class EditReviewPresenter implements EditReviewContract.Presenter, EditReviewContract.Model.OnEditReviewListener {

    private EditReviewModel model;
    private RegisterReviewView view;

    public EditReviewPresenter(RegisterReviewView view){
        model = new EditReviewModel();
        this.view = view;
    }
    @Override
    public void onEditReviewSuccess(String message) {
        view.showMessage(message);
    }

    @Override
    public void onEditReviewError(String error) {
        view.showError(error);
    }

    @Override
    public void editReview(Review review, String token) {
        model.editReview(token, review, this);
    }
}
