package com.svalero.storeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.storeapp.R;
import com.svalero.storeapp.domain.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.SuperheroHolder> {

    private Context context;
    private List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList){
        this.reviewList = reviewList;
        this.context = context;
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
        }
    }
}
