package org.webchat.client.presenter;

public class LoginPresenter implements ILoginPresenter {
    public boolean login(String userName, String userPassword) {
        boolean r = true;

        if(userName.equals("") || userPassword.equals(""))
            r = false;

        return r;
    }
}
