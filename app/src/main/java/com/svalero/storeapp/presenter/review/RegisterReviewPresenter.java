package com.svalero.storeapp.presenter.review;

import com.svalero.storeapp.contract.review.RegisterReviewContract;
import com.svalero.storeapp.domain.Review;
import com.svalero.storeapp.domain.ReviewDTO;
import com.svalero.storeapp.model.review.RegisterReviewModel;
import com.svalero.storeapp.view.RegisterReviewView;

public class RegisterReviewPresenter implements RegisterReviewContract.Presenter, RegisterReviewContract.Model.OnRegisterReviewListener {

    private RegisterReviewModel model;
    private RegisterReviewView view;

    public RegisterReviewPresenter(RegisterReviewView view){
        model = new RegisterReviewModel();
        this.view = view;
    }
    @Override
    public void onRegisterReviewSuccess(Review review) {
        view.showMessage("Review creada correctamente!");
    }

    @Override
    public void onRegisterReviewError(String message) {
        view.showError(message);
    }

    @Override
    public void registerReview(ReviewDTO review, String token) {
        model.registerReview(token, review, this);
    }
}
