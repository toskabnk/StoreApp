package com.svalero.storeapp.presenter.person;

import com.svalero.storeapp.contract.person.PersonDetailContract;
import com.svalero.storeapp.domain.Person;
import com.svalero.storeapp.model.person.PersonDetailModel;
import com.svalero.storeapp.view.MainActivity;
import com.svalero.storeapp.view.RegisterReviewView;

public class PersonDetailPresenter implements PersonDetailContract.Presenter, PersonDetailContract.Model.OnPersonDatailListener {

    private PersonDetailModel model;
    private RegisterReviewView view;

    public PersonDetailPresenter(RegisterReviewView view){
        model = new PersonDetailModel();
        this.view = view;
    }
    @Override
    public void onPersonDetailSuccess(Person person) {
        view.showPerson(person);
    }

    @Override
    public void onPersonDetailError(String message) {
        view.showError(message);
    }

    @Override
    public void getPerson(String username, String token) {
        model.getPersonDetail(username,token, this);
    }
}
