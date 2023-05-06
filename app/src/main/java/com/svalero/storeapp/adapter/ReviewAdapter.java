package com.svalero.storeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.storeapp.R;
import com.svalero.storeapp.contract.review.DeleteReviewContract;
import com.svalero.storeapp.domain.Review;
import com.svalero.storeapp.presenter.review.DeleteReviewPresenter;
import com.svalero.storeapp.view.RegisterReviewView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.SuperheroHolder> implements DeleteReviewContract.View {

    private Context context;
    private List<Review> reviewList;
    private Intent intentFrom;
    private DeleteReviewPresenter deleteReviewPresenter;
    private String token;

    public ReviewAdapter(Context context, List<Review> reviewList, Intent intentFrom, String token){
        this.reviewList = reviewList;
        this.context = context;
        this.intentFrom = intentFrom;
        this.deleteReviewPresenter = new DeleteReviewPresenter(this);
        this.token = token;
    }

    @Override
    public SuperheroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new SuperheroHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.SuperheroHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.username.setText(review.getCustomerReview().getUsername());
        holder.comment.setText(review.getComment());
        holder.rating.setText(String.valueOf(review.getRating()));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this.getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public class SuperheroHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView comment;
        public TextView rating;
        public Button editButton;
        public Button deleteButton;
        public View parentView;

        public SuperheroHolder(View view){
            super(view);
            parentView = view;

            username = view.findViewById(R.id.tvListReviewUsername);
            comment = view.findViewById(R.id.tvListReviewComment);
            rating = view.findViewById(R.id.tvListReviewRating);
            editButton = view.findViewById(R.id.bListEdit);
            deleteButton = view.findViewById(R.id.bListDelete);

            editButton.setOnClickListener(v -> editReview(getAdapterPosition()));
            deleteButton.setOnClickListener(v -> deleteReview(getAdapterPosition()));
        }
    }

    private void deleteReview(int adapterPosition) {
        Review review = reviewList.get(adapterPosition);
        deleteReviewPresenter.deleteReview(token, review.getId());
        reviewList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);

    }

    private void editReview(int adapterPosition) {
        Review review = reviewList.get(adapterPosition);
        Intent intent = new Intent(context, RegisterReviewView.class);
        String productName = review.getProductReview().getName();
        long idProduct = review.getProductReview().getId();
        String username = intentFrom.getStringExtra("username");

        intent.putExtra("username", username);
        intent.putExtra("editReview", review);
        intent.putExtra("idProduct", idProduct);
        intent.putExtra("productName", productName);
        context.startActivity(intent);
    }
}
