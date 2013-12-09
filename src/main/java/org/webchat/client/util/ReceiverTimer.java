package org.webchat.client.util;

import com.google.gwt.user.client.Timer;
import org.webchat.client.presenter.IChatPresenterForReceiver;


public class ReceiverTimer extends Timer {
    private IChatPresenterForReceiver chatPresenter;

    public ReceiverTimer(IChatPresenterForReceiver chatPresenter) {
        this.chatPresenter = chatPresenter;
    }
    @Override
    public void run() {
        chatPresenter.get();
    }
}
