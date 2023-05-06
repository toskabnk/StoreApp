package com.svalero.storeapp.contract.review;

import com.svalero.storeapp.domain.Review;
import com.svalero.storeapp.domain.ReviewDTO;

public interface RegisterReviewContract {
    interface Model{
        interface OnRegisterReviewListener {
            void onRegisterReviewSuccess(Review review);
            void onRegisterReviewError(String message);
        }
        void registerReview(String token, ReviewDTO review, RegisterReviewContract.Model.OnRegisterReviewListener listener);
    }

    interface View {
        void showError(String error);
        void showMessage(String message);
    }

    interface Presenter {
        void registerReview(ReviewDTO review, String token);
    }
}
