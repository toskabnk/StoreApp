package com.svalero.storeapp.contract.review;

import com.svalero.storeapp.domain.Review;

import java.util.List;

public interface ReviewListContract {
    interface Model{
        interface OnLoadReviewListener {
            void onLoadReviewSuccess(List<Review> reviewList);
            void onLoadReviewError(String message);
        }
        void loadAllReviewsForProduct(long id, OnLoadReviewListener listener);
    }

    interface View{
        void showReviews(List<Review> reviewList);
        void showMessage(String message);
    }

    interface Presenter {
        void loadAllReviewForProduct(long id);
    }
}
