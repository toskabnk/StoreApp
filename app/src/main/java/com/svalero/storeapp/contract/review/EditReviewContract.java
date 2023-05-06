package com.svalero.storeapp.contract.review;

import com.svalero.storeapp.domain.Review;

public interface EditReviewContract {
    interface Model {
        interface OnEditReviewListener {
            void onEditReviewSuccess(String message);
            void onEditReviewError(String error);
            }
        void editReview(String token, Review review, OnEditReviewListener listener);
        }


    interface View {
        void showError(String error);
        void showMessage(String message);
    }

    interface Presenter {
        void editReview(Review review, String token);
    }
}

