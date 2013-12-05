package org.webchat.client.util;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import org.webchat.client.presenter.IChatPresenterForGetCallback;

public class GetRequestCallback implements RequestCallback {
    private IChatPresenterForGetCallback chatPresenter;

    public GetRequestCallback(IChatPresenterForGetCallback chatPresenter) {
        this.chatPresenter = chatPresenter;
    }

    public void onResponseReceived(Request request, Response response) {
        if (response.getStatusCode() == 200)
            chatPresenter.processGetResponse(response.getText());

        else
            chatPresenter.failedReception();
    }

    public void onError(Request request, Throwable throwable) {
        chatPresenter.failedReception();
    }
}
