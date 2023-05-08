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
import android.widget.Toast;

import com.svalero.storeapp.R;
import com.svalero.storeapp.adapter.InventoryAdapter;
import com.svalero.storeapp.adapter.ProductAdapter;
import com.svalero.storeapp.contract.inventory.InventoryListContract;
import com.svalero.storeapp.db.StoreAppDatabase;
import com.svalero.storeapp.domain.Inventory;
import com.svalero.storeapp.domain.PersistData;
import com.svalero.storeapp.presenter.inventory.InventoryListPresenter;

import java.util.ArrayList;
import java.util.List;

public class InventoryListView extends AppCompatActivity implements InventoryListContract.View {
    private InventoryListPresenter inventoryListPresenter;
    private PersistData persistData;
    private String username;
    private List<Inventory> inventoryList;
    private InventoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list_view);

        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");

        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        persistData = new PersistData(0, "", "", "",false);
        try{
            persistData = db.getPersistDataDAO().getPersistData();
        }   catch (SQLiteConstraintException sce) {
            Log.i("InventoryListView" , "onCreate - Error");
            sce.printStackTrace();
        } finally {
            db.close();
        }

        inventoryListPresenter = new InventoryListPresenter(this);
        initializeRecyclerView(intentFrom);
    }

    protected void onResume(){
        super.onResume();
        inventoryListPresenter.loadAllInventories(persistData.getToken());
        adapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        getMenuInflater().inflate(R.menu.user_menu, menu);
        if(username != null){
            menu.findItem(R.id.userMenu).setVisible(true);
            menu.findItem(R.id.userMenu).setTitle(username);
            menu.findItem(R.id.menuLogin).setVisible(false);
            menu.findItem(R.id.menuLogout).setVisible(true);
            menu.findItem(R.id.menuAddProduct).setVisible(true);
            menu.findItem(R.id.menuAddReview).setVisible(false);
            menu.findItem(R.id.menuFavourite).setVisible(true);
            menu.findItem(R.id.menuInventories).setVisible(true);

        } else {
            menu.findItem(R.id.menuLogout).setVisible(false);
            menu.findItem(R.id.menuAddProduct).setVisible(false);
            menu.findItem(R.id.menuAddReview).setVisible(false);
            menu.findItem(R.id.menuFavourite).setVisible(false);
            menu.findItem(R.id.menuInventories).setVisible(false);

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
        }  else if(item.getItemId() == R.id.menuFavourite){
            Intent intent = new Intent(this, FavouritesView.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }   else if(item.getItemId() == R.id.menuInventories){
            Intent intent = new Intent(this, InventoryListView.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public void showInventories(List<Inventory> inventoryList) {
        this.inventoryList.clear();
        this.inventoryList.addAll(inventoryList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void initializeRecyclerView(Intent intentFrom){
        inventoryList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.rvInventories);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new InventoryAdapter(this, inventoryList, intentFrom, persistData.getToken());

        recyclerView.setAdapter(adapter);
    }
}