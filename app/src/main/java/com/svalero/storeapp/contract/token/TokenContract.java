package com.svalero.storeapp.contract.token;

import com.svalero.storeapp.domain.PersonLogin;
import com.svalero.storeapp.domain.Token;

public interface TokenContract {
    interface Model {
        interface OnLoadTokenListener{
            void onLoadTokenSuccess(Token token);
            void onLoadTokenError(String message);
        }
        void loadToken(PersonLogin personLogin, OnLoadTokenListener listener);
    }

    interface View {
        void showError(String ErrorMessage);
        void showToken(Token token);
    }

    interface Presenter {
        void loadToken(PersonLogin personLogin);
    }
}
