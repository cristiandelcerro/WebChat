package org.webchat.client.presenter;

import org.webchat.client.model.ChatMessage;

public interface IChatPresenter {
    public void start();
    public void sendMessage(ChatMessage message);
}
