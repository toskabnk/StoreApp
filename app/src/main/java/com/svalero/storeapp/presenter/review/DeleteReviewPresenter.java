package com.svalero.storeapp.presenter.review;

import com.svalero.storeapp.adapter.ReviewAdapter;
import com.svalero.storeapp.contract.review.DeleteReviewContract;
import com.svalero.storeapp.model.review.DeleteReviewModel;

public class DeleteReviewPresenter implements DeleteReviewContract.Presenter, DeleteReviewContract.Model.OnDeleteReviewListener {

    private DeleteReviewModel model;
    private ReviewAdapter view;

    public DeleteReviewPresenter(ReviewAdapter view){
        model = new DeleteReviewModel();
        this.view = view;
    }
    @Override
    public void onDeleteReviewSuccess(String message) {
        view.showMessage(message);
    }

    @Override
    public void onDeleteReviewError(String error) {
        view.showError(error);
    }

    @Override
    public void deleteReview(String token, long id) {
        model.deleteReview(token, id, this);
    }
}
