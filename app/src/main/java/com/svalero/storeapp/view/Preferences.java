package com.svalero.storeapp.view;

import static com.svalero.storeapp.util.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.storeapp.R;
import com.svalero.storeapp.db.StoreAppDatabase;
import com.svalero.storeapp.domain.PersistData;

public class Preferences extends AppCompatActivity {
    private CheckBox defaultRememberMe;
    private CheckBox defaultDetailCenterMe;
    private CheckBox defaultFavourite;
    private PersistData persistData;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");

        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        persistData = new PersistData(0, "", "", "",false, false ,false);
        try{
            persistData = db.getPersistDataDAO().getPersistData();
        }   catch (SQLiteConstraintException sce) {
            Log.i("InventoryListView" , "onCreate - Error");
            sce.printStackTrace();
        } finally {
            db.close();
        }

        defaultRememberMe = findViewById(R.id.defaultRememberMe);
        defaultDetailCenterMe = findViewById(R.id.defaultCenterOnMe);
        defaultFavourite = findViewById(R.id.defaultFacourite);

        defaultRememberMe.setChecked(persistData.isRememberMe());
        defaultDetailCenterMe.setChecked(persistData.isCenterOnMe());
        defaultFavourite.setChecked(persistData.isFavDefault());
    }

    public void save(View view){
        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        PersistData persistData1 = new PersistData(persistData.getId(), persistData.getUsername(), persistData.getPassword(), persistData.getToken(),
                defaultRememberMe.isChecked(), defaultDetailCenterMe.isChecked(), defaultFavourite.isChecked());
        try {
            db.getPersistDataDAO().update(persistData1);
            Snackbar.make(view, R.string.settingsSaved, BaseTransientBottomBar.LENGTH_LONG).show();
        }   catch (SQLiteConstraintException sce) {
            Log.i("Prefences - save" , "Algo ha ocurrido malo");
            Snackbar.make(view, R.string.settingsError, BaseTransientBottomBar.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }

    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }


}