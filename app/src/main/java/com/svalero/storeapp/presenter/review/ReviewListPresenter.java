package com.svalero.storeapp.presenter.review;

import com.svalero.storeapp.contract.review.ReviewListContract;
import com.svalero.storeapp.domain.Review;
import com.svalero.storeapp.model.review.ReviewListModel;

import java.util.List;

public class ReviewListPresenter implements ReviewListContract.Presenter, ReviewListContract.Model.OnLoadReviewListener {
    private ReviewListModel model;
    private ReviewListContract.View view;

    public ReviewListPresenter(ReviewListContract.View view){
        this.view = view;
        this.model = new ReviewListModel();
    }
    @Override
    public void onLoadReviewSuccess(List<Review> reviewList) {
        view.showReviews(reviewList);
    }

    @Override
    public void onLoadReviewError(String message) {
        view.showMessage(message);
    }

    @Override
    public void loadAllReviewForProduct(long id) {
        model.loadAllReviewsForProduct(id, this);
    }
}
