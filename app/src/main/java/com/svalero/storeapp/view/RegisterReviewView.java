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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.svalero.storeapp.R;
import com.svalero.storeapp.contract.person.PersonDetailContract;
import com.svalero.storeapp.contract.review.EditReviewContract;
import com.svalero.storeapp.contract.review.RegisterReviewContract;
import com.svalero.storeapp.db.StoreAppDatabase;
import com.svalero.storeapp.domain.PersistData;
import com.svalero.storeapp.domain.Person;
import com.svalero.storeapp.domain.Review;
import com.svalero.storeapp.domain.ReviewDTO;
import com.svalero.storeapp.presenter.person.PersonDetailPresenter;
import com.svalero.storeapp.presenter.review.EditReviewPresenter;
import com.svalero.storeapp.presenter.review.RegisterReviewPresenter;

public class RegisterReviewView extends AppCompatActivity implements RegisterReviewContract.View, PersonDetailContract.View, EditReviewContract.View {

    private String username;
    private String productName;
    private long idProduct;
    private TextView tvProductName;
    private EditText etComment;
    private RatingBar rbRating;
    private Button registerButton;
    private PersistData persistData;
    private Person person;
    private long personId;
    private PersonDetailPresenter personDetailPresenter;
    private RegisterReviewPresenter registerReviewPresenter;
    private EditReviewPresenter editReviewPresenter;
    private Review editReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_review_view);

        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");
        idProduct = intentFrom.getLongExtra("idProduct", 0L);
        productName = intentFrom.getStringExtra("productName");
        editReview = (Review) intentFrom.getSerializableExtra("editReview");
        Log.i("RegisterReviewView", "onCreate - Intent Username: " + username);
        Log.i("RegisterReviewView", "onCreate - Intent idProduct: " + idProduct);
        Log.i("RegisterReviewView", "onCreate - Intent productName: " + productName);

        final StoreAppDatabase db = Room.databaseBuilder(this, StoreAppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        persistData = new PersistData(0, "", "", "",false, false ,false);
        try{
            persistData = db.getPersistDataDAO().getPersistData();
        }   catch (SQLiteConstraintException sce) {
            Log.i("RegisterReviewView" , "onCreate - Error");
            sce.printStackTrace();
        } finally {
            db.close();
        }

        tvProductName = findViewById(R.id.registerReviewProductName);
        etComment = findViewById(R.id.registerReviewComment);
        rbRating = findViewById(R.id.ratingBar);
        registerButton = findViewById(R.id.button2);
        registerButton.setEnabled(false);

        personDetailPresenter = new PersonDetailPresenter(this);
        registerReviewPresenter = new RegisterReviewPresenter(this);

        if(editReview != null){
            etComment.setText(editReview.getComment());
            rbRating.setRating(editReview.getRating());
            idProduct = editReview.getProductReview().getId();
            personId = editReview.getCustomerReview().getId();
            registerButton.setText(R.string.editButton);
            editReviewPresenter = new EditReviewPresenter(this);
        }

        tvProductName.setText(productName);

        if(username != null){
            personDetailPresenter.getPerson(username, persistData.getToken());
        }
    }


    public void showError(String error){
        Snackbar.make(((EditText) findViewById(R.id.registerReviewComment)), error,
                BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void showPerson(Person person) {
        registerButton.setEnabled(true);
        this.person = person;
        personId = person.getId();
        Log.i("RegisterReviewView", "showPerson - Person.getID: " + this.person.getId());
        Log.i("RegisterReviewView", "showPerson - personId: " + personId);
    }

    public void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ProductDetailsView.class);
        intent.putExtra("username", username);
        intent.putExtra("idProduct", idProduct);
        startActivity(intent);

    }

    public void register(View view){
        String comment = etComment.getText().toString();
        float rating = rbRating.getRating();

        Log.i("RegisterReviewView", "register - personId: " + personId);
        Log.i("RegisterReviewView", "register - this.personId: " + this.personId);

        if(editReview != null){
            Review review = new Review(editReview.getId(), null, null, rating, comment, null);
            editReviewPresenter.editReview(review, persistData.getToken());

        } else {
            long personId = person.getId();

            ReviewDTO reviewDTO = new ReviewDTO(personId, idProduct, rating, comment);

            registerReviewPresenter.registerReview(reviewDTO, persistData.getToken());
        }
    }
}