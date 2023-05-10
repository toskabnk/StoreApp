package com.svalero.storeapp.view;

import static com.svalero.storeapp.util.Constants.DATABASE_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.storeapp.R;
import com.svalero.storeapp.adapter.ReviewAdapter;
import com.svalero.storeapp.contract.product.ProductDetailsContract;
import com.svalero.storeapp.contract.review.ReviewListContract;
import com.svalero.storeapp.db.StoreAppDatabase;
import com.svalero.storeapp.domain.Favourites;
import com.svalero.storeapp.domain.PersistData;
import com.svalero.storeapp.domain.Product;
import com.svalero.storeapp.domain.Review;
import com.svalero.storeapp.presenter.product.ProductDetailsPresenter;
import com.svalero.storeapp.presenter.review.ReviewListPresenter;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsView extends AppCompatActivity implements ProductDetailsContract.View, ReviewListContract.View {

    private List<Review> reviewList;
    private ReviewAdapter adapter;
    private ReviewListPresenter reviewListPresenter;
    private ProductDetailsPresenter presenter;
    private Product product;
    private TextView tvName;
    private TextView tvDetails;
    private TextView tvPrice;
    private long productId;
    private String username;
    private  PersistData persistData;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_view);
        Intent intentFrom = getIntent();
        productId = intentFrom.getLongExtra("productId", 0L);
        username = intentFrom.getStringExtra("username");
        if(productId == 0L){
            return;
        }

        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        persistData = new PersistData(0, "", "", "",false, false ,false);        try{
            persistData = db.getPersistDataDAO().getPersistData();
        }   catch (SQLiteConstraintException sce) {
            Log.i("RegisterProductView" , "onCreate - Error");
            sce.printStackTrace();
        } finally {
            db.close();
        }

        presenter = new ProductDetailsPresenter(this);
        tvName = findViewById(R.id.tvProductDetailsName);
        tvDetails = findViewById(R.id.tvProductDetailsDescription);
        tvPrice = findViewById(R.id.tvProductDetailsPrice);
        checkBox = findViewById(R.id.favouriteProduct);
        presenter.loadProduct(productId);

        reviewListPresenter = new ReviewListPresenter(this);
        checkBox.setOnClickListener(v -> setFavourite());
        initializeRecyclerView(intentFrom);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.user_menu, menu);
        if(username != null){
            menu.findItem(R.id.userMenu).setVisible(true);
            menu.findItem(R.id.userMenu).setTitle(username);
            menu.findItem(R.id.menuLogin).setVisible(false);
            menu.findItem(R.id.menuLogout).setVisible(true);
            menu.findItem(R.id.menuAddProduct).setVisible(false);
            menu.findItem(R.id.menuAddReview).setVisible(true);
            menu.findItem(R.id.menuFavourite).setVisible(true);
            menu.findItem(R.id.menuInventories).setVisible(true);
            menu.findItem(R.id.menuPreferences).setVisible(true);
        } else {
            menu.findItem(R.id.menuLogout).setVisible(false);
            menu.findItem(R.id.menuAddProduct).setVisible(false);
            menu.findItem(R.id.menuAddReview).setVisible(false);
            menu.findItem(R.id.menuFavourite).setVisible(false);
            menu.findItem(R.id.menuInventories).setVisible(false);
            menu.findItem(R.id.menuPreferences).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuLogin){
            Intent intent = new Intent(this, LoginView.class);
            startActivity(intent);
            return true;
        } else if(item.getItemId() == R.id.menuAddProduct){
            Intent intent = new Intent(this, RegisterProductView.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menuAddReview){
            Intent intent = new Intent(this, RegisterReviewView.class);
            intent.putExtra("username", username);
            intent.putExtra("idProduct", productId);
            intent.putExtra("productName", product.getName());
            startActivity(intent);
        } else if(item.getItemId() == R.id.menuLogout){
            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
            deleteDialog.setMessage(R.string.confirmationMessage).setTitle(R.string.logoutMessage)
                    .setPositiveButton(R.string.confirmationYes, (dialog, id) -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }).setNegativeButton(R.string.confirmationNo, (dialog, id) -> {
                        dialog.dismiss();
                    });
            AlertDialog dialog = deleteDialog.create();
            dialog.show();
        } else if(item.getItemId() == R.id.menuFavourite){
            Intent intent = new Intent(this, FavouritesView.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menuInventories){
            Intent intent = new Intent(this, InventoryListView.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menuPreferences){
            Intent intent = new Intent(this, Preferences.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        return false;
    }

    private void initializeRecyclerView(Intent intentFrom) {
        reviewList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rvReviews);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ReviewAdapter(this, reviewList, intentFrom, persistData.getToken());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProduct(Product product) {
        tvName.setText(product.getName());
        tvDetails.setText(product.getDescription());
        tvPrice.setText(String.valueOf(product.getPrice()));
        this.product = product;
        setCheckboxed();
    }


    private void setCheckboxed() {
        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        try {
            Favourites favourites = db.getFavouriteDAO().getFavourite(productId, username);
            if(favourites != null){
                checkBox.setChecked(true);
                Log.i("ProductDetailsView" , "setCheckboxed - Favourite: " + favourites.toString());
            } else {
                checkBox.setChecked(false);
                Log.i("ProductDetailsView" , "setCheckboxed - No favorito");

            }
        } catch (SQLiteConstraintException sce) {
            Log.i("ProductDetailsView" , "setCheckboxed - Error");
            sce.printStackTrace();
        } finally {
            db.close();
        }
    }

    private void setFavourite() {
        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        try {
            if (checkBox.isChecked()) {
                Favourites insert = new Favourites(0L, product.getId(), product.getName(), username);
                Log.i("ProductDetailsView" , "setFavourite - Favourite: " + insert.toString());
                db.getFavouriteDAO().insert(insert);
            } else {
                Favourites delete = db.getFavouriteDAO().getFavourite(product.getId(), username);
                Log.i("ProductDetailsView" , "setFavourite - Favourite: " + delete.toString());
                db.getFavouriteDAO().delete(delete);
            }
        } catch (SQLiteConstraintException sce) {
            Log.i("ProductAdapter" , "setFavourite - Error");
            sce.printStackTrace();
        } finally {
            db.close();
        }
    }

    @Override
    public void showReviews(List<Review> reviewList) {
        this.reviewList.clear();
        this.reviewList.addAll(reviewList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reviewListPresenter.loadAllReviewForProduct(productId);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}