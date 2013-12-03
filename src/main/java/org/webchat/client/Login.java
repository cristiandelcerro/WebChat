package org.webchat.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Login implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    private final Messages messages = GWT.create(Messages.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button loginButton = new Button(messages.loginButton());
        final TextBox nameField = new TextBox();
        final TextBox passwordField = new TextBox();
        final Label errorLabel = new Label();

        nameField.setText(messages.nameField());
        passwordField.setText(messages.passwordField());

        // We can add style names to widgets
        loginButton.addStyleName("sendButton");

        loginButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String userName = nameField.getText();
                if (userName.equals(""))
                    errorLabel.setText("Usuario y/o contraseña incorrectos.");
                else
                    errorLabel.setText("Bienvenido, " + userName);
            }
        });

        RootPanel.get("nameFieldContainer").add(nameField);
        RootPanel.get("passwordFieldContainer").add(passwordField);
        RootPanel.get("loginButtonContainer").add(loginButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);
    }
}
