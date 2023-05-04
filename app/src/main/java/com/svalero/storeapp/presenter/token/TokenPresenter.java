package com.svalero.storeapp.presenter.token;

import com.svalero.storeapp.contract.token.TokenContract;
import com.svalero.storeapp.domain.PersonLogin;
import com.svalero.storeapp.domain.Token;
import com.svalero.storeapp.model.token.TokenModel;

public class TokenPresenter implements TokenContract.Presenter, TokenContract.Model.OnLoadTokenListener {

    private TokenModel model;
    private TokenContract.View view;

    public TokenPresenter(TokenContract.View view){
        this.view = view;
        this.model = new TokenModel();
    }
    @Override
    public void onLoadTokenSuccess(Token token) {
        view.showToken(token);
    }

    @Override
    public void onLoadTokenError(String message) {
        view.showError(message);
    }

    @Override
    public void loadToken(PersonLogin personLogin) {
        model.loadToken(personLogin, this);
    }
}
