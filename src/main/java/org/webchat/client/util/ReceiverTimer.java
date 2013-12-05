package org.webchat.client.util;

import com.google.gwt.user.client.Timer;
import org.webchat.client.presenter.IChatPresenterForReceiver;

/**
 * Created with IntelliJ IDEA.
 * User: cristianbq
 * Date: 5/12/13
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
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
