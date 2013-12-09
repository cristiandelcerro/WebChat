package org.webchat.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import org.webchat.client.presenter.ILoginPresenter;
import org.webchat.client.Messages;
import org.webchat.client.presenter.LoginPresenter;

public class LoginView {
    private ILoginPresenter loginPresenter;

    private Messages messages;
    private VerticalPanel loginPanel;
    private TextBox nameField;
    private TextBox passwordField;
    private Label loginLabel;
    private Label passwordLabel;
    private Label errorLabel;
    private Button loginButton;
    private boolean alreadyStarted;
    private ChatView chatView;

    public LoginView(Messages messages) {
        this.loginPresenter = new LoginPresenter();
        this.messages = messages;
        this.alreadyStarted = false;
        chatView = new ChatView(messages, this);
    }

    private void createWidgets() {
        nameField = new TextBox();
        passwordField = new TextBox();
        loginLabel = new Label(messages.nameField());
        passwordLabel = new Label(messages.passwordField());
        errorLabel = new Label();

        createLoginButton();
    }

    private void createLoginButton() {
        loginButton = new Button(messages.loginButton());
        loginButton.addClickHandler(new LoginButtonHandler(this));
    }

    public void onLoginButtonClicked() {
        String userName = nameField.getText();

        boolean loginSuccessful = loginPresenter.login(userName, passwordField.getText());

        if (loginSuccessful) {
            stop();
            startChatView(userName);
        }

        else
            errorLabel.setText(messages.incorrectUser());
    }

    private void startChatView(String userName) {
        chatView.start(userName);
    }

    private void addWidgetsToChatPanel() {
        loginPanel.add(loginLabel);
        loginPanel.add(nameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        loginPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        loginPanel.add(loginButton);

        loginPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        loginPanel.add(errorLabel);

        loginLabel.setHeight("100%");
        passwordLabel.setHeight("100%");
    }

    public void start() {
        if (alreadyStarted)
            loginPanel.setVisible(true);

        else {
            alreadyStarted = true;
            RootPanel chatContainer = RootPanel.get("chatContainer");
            chatContainer.setStyleName("loginView");
            loginPanel = new VerticalPanel();

            createWidgets();
            addWidgetsToChatPanel();

            chatContainer.add(loginPanel);
        }


    }

    private void stop() {
//        chatContainer.remove(loginPanel);
        loginPanel.setVisible(false);
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
