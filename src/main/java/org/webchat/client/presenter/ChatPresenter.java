package org.webchat.client.presenter;

import com.google.gwt.http.client.*;
import org.webchat.client.model.ChatMessage;
import org.webchat.client.util.Constants;
import org.webchat.client.util.GetRequestCallback;
import org.webchat.client.util.PostRequestCallback;
import org.webchat.client.view.IChatView;

import java.util.LinkedList;

public class ChatPresenter implements IChatPresenter, IChatPresenterForPostCallback, IChatPresenterForGetCallback {
    private String userName;
    private IChatView chatView;
//    private ReceiverTimer receiverTimer;
//    private SenderThread senderThread;
    private boolean stopped;
    private int lastSeq;

    public ChatPresenter(String userName, IChatView chatView) {
        this.userName = userName;
        this.chatView = chatView;
        stopped = true;
    }

//    private void createTimers() {
//        senderThread = new SenderThread();
//        senderThread.start();
//        receiverTimer = new ReceiverTimer(this);
//    }
//
//    synchronized void start() {
//        if (!stopped) return;
//        stopped = false;
//
//        restoreLastSeq();
//        createTimers();
//    }
//
//    synchronized void stop() {
//        if (stopped) return;
//        stopped = true;
//
//        destroyTimers();
//        saveLastSeq();
//    }

    public void sendMessage(ChatMessage message) {
        if(!message.getMessage().equals("") && !message.getNick().equals("")) {
//            senderThread.prepareMessageToSend(message);
            String messageToSend = message.toJSON();
            post(messageToSend);
            get();
        }

    }

    private void post(String messageToSend) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, Constants.URL);
        builder.setHeader("content-type", "application/json");
        PostRequestCallback postRequestCallback = new PostRequestCallback(this);

        try {
            builder.sendRequest(messageToSend, postRequestCallback);
        }

        catch (RequestException e) {
            chatView.setNotificationsLabelText("No se ha podido enviar el mensaje correctamente.");
        }
    }

    private void get() {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, Constants.URL + "?next_seq=0");
        builder.setHeader("content-type", "application/json");
        GetRequestCallback getRequestCallback = new GetRequestCallback(this);

        try {
            builder.sendRequest(null, getRequestCallback);
        }

        catch (RequestException e) {
            chatView.setNotificationsLabelText("No se ha podido enviar el mensaje correctamente.");
        }
    }

    public void failedSending() {
        chatView.setNotificationsLabelText("No se ha podido enviar el mensaje correctamente.");
    }

    public void failedReception() {
        chatView.setNotificationsLabelText("Ha fallado la recepción de mensajes.");
    }

    public void processGetResponse(String getResponse) {
        LinkedList<String> newList = new LinkedList<String>();
        newList.add(getResponse);
        chatView.addToMessageList(newList);
    }

//    void receiveMessages(ServerResponse serverResponse) {
//        synchronized (this) {
//            if (stopped) return;
//            lastSeq = serverResponse.getNextSeq();
//        }
//
//        messageList.addAll(serverResponse.getMessages());
//        chatActivityHandler.sendEmptyMessage(0);
//    }
//
//    private void destroyTimers() {
//        senderThread.finish();
//        receiverTimer.cancel();
//
//        try {
//            senderThread.join();
//        }
//
//        catch (InterruptedException e) {
//            System.err.println("El hilo enviador ha terminado mal.");
//            System.err.print(e.toString());
//        }
//    }
//
//    synchronized int getLastSeq() {
//        return lastSeq;
//    }
//
//    private void saveLastSeq() {
//        SharedPreferences settings = chatActivity.getSharedPreferences(savedProperties, ChatActivity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putInt(userName, lastSeq);
//        editor.commit();
//    }
//
//    private void restoreLastSeq() {
//        SharedPreferences settings = chatActivity.getSharedPreferences(savedProperties, ChatActivity.MODE_PRIVATE);
//        this.lastSeq = settings.getInt(userName, 0);
//    }
//
//    synchronized void setStopped(boolean stopped) {
//        this.stopped = stopped;
//    }
//
//    void setReceiverTimer(ReceiverTimer receiverTimer) {
//        this.receiverTimer = receiverTimer;
//    }
//
//    SenderThread getSenderThread() {
//        return senderThread;
//    }
//
//    void setSenderThread(SenderThread senderThread) {
//        this.senderThread = senderThread;
//    }
}
