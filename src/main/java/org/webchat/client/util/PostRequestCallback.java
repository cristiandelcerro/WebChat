package org.webchat.client.util;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import org.webchat.client.presenter.IChatPresenterForPostCallback;

public class PostRequestCallback implements RequestCallback {
    private IChatPresenterForPostCallback chatPresenter;
    private String messageToSend;

    public PostRequestCallback(IChatPresenterForPostCallback chatPresenter, String messageToSend) {
        this.chatPresenter = chatPresenter;
        this.messageToSend = messageToSend;
    }

    public void onResponseReceived(Request request, Response response) {
        if (response.getStatusCode() != 200) chatPresenter.failedSending(messageToSend);
    }

    public void onError(Request request, Throwable throwable) {
        chatPresenter.failedSending(messageToSend);
    }
}
