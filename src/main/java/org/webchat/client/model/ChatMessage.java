package org.webchat.client.model;

public class ChatMessage implements IChatMessage {
    private String nick;
    private String message;

    public ChatMessage(String nick, String message) {
        this.nick = nick;
        this.message = message;
    }

    public String toJSON() {
        return "{\"nick\": \"" + nick + "\", \"message\": \"" + message + "\"}";
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
