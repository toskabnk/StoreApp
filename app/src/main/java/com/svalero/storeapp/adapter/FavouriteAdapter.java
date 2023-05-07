package com.svalero.storeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.storeapp.R;
import com.svalero.storeapp.domain.Favourites;
import com.svalero.storeapp.view.ProductDetailsView;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.SuperheroHolder> {
    private Context context;
    private List<Favourites> favouritesList;
    private Intent intentFrom;

    public FavouriteAdapter(Context context, List<Favourites> favouritesList, Intent intentFrom){
        this.context = context;
        this.favouritesList = favouritesList;
        this.intentFrom = intentFrom;
    }

    @NonNull
    @Override
    public FavouriteAdapter.SuperheroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_item, parent, false);
        return new FavouriteAdapter.SuperheroHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.SuperheroHolder holder, int position) {
        holder.name.setText(favouritesList.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return favouritesList.size();
    }

    public class SuperheroHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Button details;

        public SuperheroHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvFavouriteName);
            details = view.findViewById(R.id.btFavDetails);

            details.setOnClickListener(v -> seeProductDetails(getAdapterPosition()));
        }
    }

    private void seeProductDetails(int adapterPosition) {
        Favourites favourites = favouritesList.get(adapterPosition);
        Intent intent = new Intent(context, ProductDetailsView.class);
        String username = intentFrom.getStringExtra("username");
        intent.putExtra("username", username);
        intent.putExtra("productId", favourites.getIdProduct());
        context.startActivity(intent);
    }

}
