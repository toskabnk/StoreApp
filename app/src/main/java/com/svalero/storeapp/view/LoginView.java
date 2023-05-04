package com.svalero.storeapp.view;

import static com.svalero.storeapp.util.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        etUsernmae = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);

        presenter = new TokenPresenter(this);
    }

    @Override
    public void showError(String ErrorMessage) {
        Toast.makeText(this, ErrorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToken(Token token) {
        if(token != null) {
            final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

            try {
                persistData = db.getPersistDataDAO().getPersistData();
                persistData.setToken(token.getToken());
                db.getPersistDataDAO().update(persistData);
            } catch (SQLiteConstraintException sce) {
                Log.i("LoginView - showToken", "Algo ha ocurrido malo");
                Snackbar.make(this.getCurrentFocus(), "Algo ha ocurrido malo", BaseTransientBottomBar.LENGTH_LONG).show();
            } finally {
                db.close();
            }
            Log.i("LoginView - showToken", "Token guardado!");
            Log.i("LoginView - showToken", token.getToken());
            Snackbar.make(this.getCurrentFocus(), "Login Correcto", BaseTransientBottomBar.LENGTH_LONG).show();
            Intent intent = new Intent(LoginView.this, MainActivity.class);
            intent.putExtra("username", token.getUsername());
            startActivity(intent);
        } else {
            Snackbar.make(this.getCurrentFocus(), "Login Incorrecto", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

    public void login(View view){
        String username = etUsernmae.getText().toString();
        String password = etPassword.getText().toString();

        PersonLogin personLogin = new PersonLogin(username, password);

        presenter.loadToken(personLogin);
    }
}