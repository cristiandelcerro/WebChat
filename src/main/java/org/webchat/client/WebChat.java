package org.webchat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import org.webchat.client.view.LoginView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WebChat implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        Messages messages = GWT.create(Messages.class);
        LoginView loginView = new LoginView(messages);
        loginView.start();

    }
}
