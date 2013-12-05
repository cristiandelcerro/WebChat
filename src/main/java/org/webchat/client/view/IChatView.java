package org.webchat.client.view;

import java.util.List;

public interface IChatView {
    public void addToMessageList(List<String> newMessages);
    public void setNotificationsLabelText(String text);
}
