package org.webchat.client.presenter;

import org.webchat.client.model.ChatMessage;

public interface IChatPresenter {
    public void sendMessage(ChatMessage message);
}
