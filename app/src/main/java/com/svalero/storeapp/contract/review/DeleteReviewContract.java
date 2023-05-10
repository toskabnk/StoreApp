package com.svalero.storeapp.contract.review;

public interface DeleteReviewContract {
    interface Model {
        interface OnDeleteReviewListener {
            void onDeleteReviewSuccess(String message);
            void onDeleteReviewError(String error);
        }
        void deleteReview(String token, long id, OnDeleteReviewListener listener);
    }

    interface View {
        void showError(String error);
        void showMessage(String message);
    }

    interface Presenter {
        void deleteReview(String token, long id);
    }
}
