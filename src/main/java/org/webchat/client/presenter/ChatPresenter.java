package org.webchat.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Cookies;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import org.webchat.client.Messages;
import org.webchat.client.model.*;
import org.webchat.client.util.Constants;
import org.webchat.client.util.GetRequestCallback;
import org.webchat.client.util.PostRequestCallback;
import org.webchat.client.util.ReceiverTimer;
import org.webchat.client.view.IChatView;

import java.util.LinkedList;
import java.util.List;

public class ChatPresenter implements IChatPresenter, IChatPresenterForPostCallback, IChatPresenterForGetCallback,
    IChatPresenterForReceiver {

    private String userName;
    private IChatView chatView;
    private int lastSeq;
    private ReceiverTimer receiverTimer;

    public ChatPresenter(IChatView chatView) {
        this.lastSeq = 0;
        this.chatView = chatView;
    }

    public void start(String userName) {
        this.userName = userName;
        String lastSeqString = Cookies.getCookie(userName);

        if (lastSeqString == null) lastSeq = 0;
        else                       lastSeq = Integer.parseInt(lastSeqString);

        receiverTimer = new ReceiverTimer(this);
        receiverTimer.scheduleRepeating(Constants.timeToRepeatReceiver);
    }

    public void stop() {
        receiverTimer.cancel();
    }

    public void sendMessage(ChatMessage message) {
        if(message.getMessage().equals("") || message.getNick().equals(""))
            return;

        String messageToSend = message.toJSON();
        post(messageToSend);
    }

    private void post(String messageToSend) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, Constants.URL);
        builder.setHeader("content-type", "application/json");
        PostRequestCallback postRequestCallback = new PostRequestCallback(this, messageToSend);

        try {
            builder.sendRequest(messageToSend, postRequestCallback);
        }

        catch (RequestException e) {
            failedSending(messageToSend);
        }
    }

    public void get() {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Constants.URL + "?next_seq=" + lastSeq);
        builder.setHeader("content-type", "application/json");
        GetRequestCallback getRequestCallback = new GetRequestCallback(this);

        try {
            builder.sendRequest(null, getRequestCallback);
        }

        catch (RequestException e) {
            chatView.notifyFailedReception();
        }
    }

    public void failedSending(String messageToSend) {
        chatView.notifyFailedSending();
        post(messageToSend);
    }

    public void failedReception() {
        chatView.notifyFailedReception();
    }

    public void processGetResponse(String getResponse) {
        ServerResponseAutoBeanFactory serverResponseAutoBeanFactory = GWT.create(ServerResponseAutoBeanFactory.class);
        AutoBean<IServerResponse> autoBean = AutoBeanCodex.decode(serverResponseAutoBeanFactory, IServerResponse.class,
                getResponse);
        IServerResponse serverResponse = autoBean.as();
        lastSeq = serverResponse.getNextSeq();
        Cookies.setCookie(userName, Integer.toString(lastSeq));
        chatView.addToMessageList(serverResponse.getMessages());
    }
}
