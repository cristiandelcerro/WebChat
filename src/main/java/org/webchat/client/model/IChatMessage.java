package org.webchat.client.model;

public interface IChatMessage {
    public void setNick(String nick);
    public String getNick();
    public String getMessage();
    public void setMessage(String message);
}
