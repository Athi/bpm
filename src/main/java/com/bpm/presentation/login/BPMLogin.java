package com.bpm.presentation.login;

import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

/**
 * Created by Athi
 */
public class BPMLogin extends LoginForm {

    public BPMLogin(LoginCallback callback) {
        super();
        setSizeUndefined();
        addLoginListener(loginEvent -> {
            String username = loginEvent.getLoginParameter("username");
            String password = loginEvent.getLoginParameter("password");
            ((PasswordField) this.getState().passwordFieldConnector).setValue("");
            if (!callback.login(username, password)) {
                Notification.show("Login failed", Notification.Type.ERROR_MESSAGE);
                ((TextField) this.getState().userNameFieldConnector).focus();
            }
        });
    }

    @FunctionalInterface
    public interface LoginCallback {

        boolean login(String username, String password);
    }
}
