package com.svalero.storeapp.view;

import static com.svalero.storeapp.util.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.storeapp.R;
import com.svalero.storeapp.contract.token.TokenContract;
import com.svalero.storeapp.db.StoreAppDatabase;
import com.svalero.storeapp.domain.PersistData;
import com.svalero.storeapp.domain.PersonLogin;
import com.svalero.storeapp.domain.Token;
import com.svalero.storeapp.presenter.token.TokenPresenter;

public class LoginView extends AppCompatActivity implements TokenContract.View {

    private PersistData persistData;
    private EditText etUsernmae;
    private EditText etPassword;
    private TokenPresenter presenter;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        etUsernmae = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        loginButton = findViewById(R.id.button);

        presenter = new TokenPresenter(this);

        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        persistData = new PersistData(0, "", "", "",false, false ,false);
        try{
            persistData = db.getPersistDataDAO().getPersistData();
            if(persistData.isRememberMe()){
                etUsernmae.setText(persistData.getUsername());
                etPassword.setText(persistData.getPassword());
                login(loginButton);
            }
        }   catch (SQLiteConstraintException sce) {
            Log.i("InventoryListView" , "onCreate - Error");
            sce.printStackTrace();
        } finally {
            db.close();
        }

    }

    @Override
    public void showError(String ErrorMessage) {
        Toast.makeText(this, ErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToken(Token token) {
        if(token != null) {
            final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
            persistData = new PersistData(0, "", "", "",false, false ,false);
            try {
                persistData = db.getPersistDataDAO().getPersistData();
                persistData.setToken(token.getToken());
                persistData.setPassword(etPassword.getText().toString());
                persistData.setUsername(etUsernmae.getText().toString());
                db.getPersistDataDAO().update(persistData);

            } catch (SQLiteConstraintException sce) {
                Log.i("LoginView - showToken", "Algo ha ocurrido malo");
                Snackbar.make(loginButton, "Algo ha ocurrido malo", BaseTransientBottomBar.LENGTH_LONG).show();
            } finally {
                db.close();
            }
            Log.i("LoginView - showToken", "Token guardado!");
            Log.i("LoginView - showToken", token.getToken());
            Log.i("LoginView - showToken", persistData.toString());
            Snackbar.make(loginButton, "Login Correcto", BaseTransientBottomBar.LENGTH_LONG).show();
            if(persistData.isFavDefault()){
                Log.i("LoginView - showToken", "Favoritos");
                Intent intentFav = new Intent(LoginView.this, FavouritesView.class);
                intentFav.putExtra("username", token.getUsername());
                startActivity(intentFav);
            } else {
                Intent intent = new Intent(LoginView.this, MainActivity.class);
                intent.putExtra("username", token.getUsername());
                startActivity(intent);
            }

        } else {
            Snackbar.make(loginButton, "Login Incorrecto", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    public void login(View view){
        String username = etUsernmae.getText().toString();
        String password = etPassword.getText().toString();

        PersonLogin personLogin = new PersonLogin(username, password);

        presenter.loadToken(personLogin);
    }
}