package org.webchat.client.view;

import org.webchat.client.model.IChatMessage;

import java.util.List;

public interface IChatView {
    public void addToMessageList(List<IChatMessage> newMessages);
    public void setNotificationsLabelText(String text);
}
