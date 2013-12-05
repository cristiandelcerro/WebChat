package org.webchat.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import org.webchat.client.presenter.ILoginPresenter;
import org.webchat.client.Messages;
import org.webchat.client.presenter.LoginPresenter;

public class LoginView {
    private ILoginPresenter loginPresenter;

    private RootPanel chatContainer;
    private Messages messages;
    private VerticalPanel loginPanel;
    private TextBox nameField;
    private TextBox passwordField;
    private Label loginLabel;
    private Label passwordLabel;
    private Label errorLabel;
    private Button loginButton;

    public LoginView(Messages messages) {
        this.loginPresenter = new LoginPresenter();
        this.messages = messages;
    }

    private void createWidgets() {
        nameField = new TextBox();
        passwordField = new TextBox();
        loginLabel = new Label(messages.nameField());
        passwordLabel = new Label(messages.passwordField());
        errorLabel = new Label();

        nameField.setText(messages.nameField());
        passwordField.setText(messages.passwordField());

        createLoginButton();
    }

    private void createLoginButton() {
        loginButton = new Button(messages.loginButton());

        // We can add style names to widgets
//        loginButton.addStyleName("sendButton");

        loginButton.addClickHandler(new LoginButtonHandler(this));
    }

    public void onLoginButtonClicked() {
        String userName = nameField.getText();

        boolean loginSuccessful = loginPresenter.login(userName, passwordField.getText());

        if (loginSuccessful) {
            errorLabel.setText("Bienvenido, " + userName);
            stop();
            startChatView(userName);
        }

        else
            errorLabel.setText("Usuario y/o contraseña incorrectos.");
    }

    private void startChatView(String userName) {
        ChatView chatView = new ChatView(userName, messages);
        chatView.start();
    }

    private void addWidgetsToChatPanel() {
        loginPanel.add(loginLabel);
        loginPanel.add(nameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(errorLabel);
    }

    public void start() {
        chatContainer = RootPanel.get("chatContainer");
        loginPanel = new VerticalPanel();

        createWidgets();
        addWidgetsToChatPanel();

        chatContainer.add(loginPanel);
    }

    private void stop() {
        chatContainer.remove(loginPanel);
    }

    class LoginButtonHandler implements ClickHandler {
        private LoginView loginView;

        public LoginButtonHandler(LoginView loginView) {
            this.loginView = loginView;
        }

        public void onClick(ClickEvent event) {
            loginView.onLoginButtonClicked();
        }
    }


}
