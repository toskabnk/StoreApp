package com.svalero.storeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.svalero.storeapp.R;
import com.svalero.storeapp.contract.product.DeleteProductContract;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.presenter.product.DeleteProductPresenter;
import com.svalero.storeapp.view.ProductDetailsView;
import com.svalero.storeapp.view.RegisterProductView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.SuperheroHolder> implements DeleteProductContract.View {
    private Context context;
    private List<Product> productList;
    private Intent intentFrom;
    private DeleteProductPresenter deleteProductPresenter;
    private String token;
    //private View snackBarView;

    public ProductAdapter(Context context, List<Product> productList, Intent intentFrom, String token){
        this.context = context;
        this.productList = productList;
        this.intentFrom = intentFrom;
        this.deleteProductPresenter = new DeleteProductPresenter(this);
        this.token = token;
    }

    public Context getContext() {
        return context;
    }

    @NonNull
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

    @Override
    public void showError(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
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
            productEdit.setOnClickListener(v -> editProduct(getAdapterPosition()));
            productDelete.setOnClickListener(v -> deleteProduct(getAdapterPosition()));
        }
    }

    private void seeProductDetails(int adapterPosition) {
        Product product = productList.get(adapterPosition);
        Intent intent = new Intent(context, ProductDetailsView.class);
        intent.putExtra("productId", product.getId());
        context.startActivity(intent);
    }

    private void editProduct(int adapterPosition){
        Product product = productList.get(adapterPosition);
        Intent intent = new Intent(context, RegisterProductView.class);
        String username = intentFrom.getStringExtra("username");
        Log.i("ProductAdapter" , "editProduct - " + username);
        if(username == null){
            username = "";
        }
        intent.putExtra("username", username);
        intent.putExtra("editProduct", product);
        context.startActivity(intent);

    }

    private void deleteProduct(int adapterPosition){
        Product product = productList.get(adapterPosition);
        deleteProductPresenter.deleteProduct(product.getId(), token);
        //TODO: Si no se borra correctamente, no borrar de la lista
        productList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }
}
