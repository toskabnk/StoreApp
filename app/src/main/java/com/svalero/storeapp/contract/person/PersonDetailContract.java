package com.svalero.storeapp.contract.person;

import com.svalero.storeapp.domain.Person;

public interface PersonDetailContract {
    interface Model {
        interface OnPersonDatailListener {
            void onPersonDetailSuccess(Person person);
            void onPersonDetailError(String message);
        }
        void getPersonDetail(String username, String token, OnPersonDatailListener listener);
    }

    interface View {
        void showError(String error);
        void showPerson(Person person);
    }

    interface Presenter {
        void getPerson(String username, String token);
    }
}
