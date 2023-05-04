package com.svalero.storeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.svalero.storeapp.R;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.view.ProductDetailsView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.SuperheroHolder>{
    private Context context;
    private List<Product> productList;
    //private View snackBarView;

    public ProductAdapter(Context context, List<Product> productList){
        this.context = context;
        this.productList = productList;
    }

    public Context getContext() {
        return context;
    }

    public SuperheroHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new SuperheroHolder(view);
    }

    @Override
    public void onBindViewHolder(SuperheroHolder holder, int position) {
        holder.productName.setText(productList.get(position).getName());
        //TODO: Imagen por Base64?
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class SuperheroHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public Button productDetails;
        public Button productEdit;
        public Button productDelete;
        public ImageView imageView;
        public View parentView;

        public SuperheroHolder(View view){
            super(view);
            parentView = view;

            productName = view.findViewById(R.id.tvListProductName);
            productDetails = view.findViewById(R.id.bListDetails);
            productEdit = view.findViewById(R.id.bListEdit);
            productDelete = view.findViewById(R.id.bListDelete);
            imageView = view.findViewById(R.id.ivListDetailsImage);

            productDetails.setOnClickListener(v -> seeProductDetails(getAdapterPosition()));
        }
    }

    private void seeProductDetails(int adapterPosition) {
        Product product = productList.get(adapterPosition);
        Intent intent = new Intent(context, ProductDetailsView.class);
        intent.putExtra("productId", product.getId());
        context.startActivity(intent);
    }
}
