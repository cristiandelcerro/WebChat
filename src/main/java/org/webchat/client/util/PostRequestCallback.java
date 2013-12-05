package org.webchat.client.util;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import org.webchat.client.presenter.IChatPresenterForPostCallback;

public class PostRequestCallback implements RequestCallback {
    private IChatPresenterForPostCallback chatPresenter;

    public PostRequestCallback(IChatPresenterForPostCallback chatPresenter) {
        this.chatPresenter = chatPresenter;
    }

    public void onResponseReceived(Request request, Response response) {
        if (response.getStatusCode() != 200) chatPresenter.failedSending();
    }

    public void onError(Request request, Throwable throwable) {
        chatPresenter.failedSending();
    }
}
