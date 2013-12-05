package org.webchat.client.model;

//import com.google.web.bindery.autobean.shared.AutoBean;
//import com.google.web.bindery.autobean.shared.AutoBeanCodex;
//import com.google.web.bindery.autobean.shared.AutoBeanUtils;

public class ChatMessage {
    private String nick;
    private String message;

    public static ChatMessage messageFactory(String nick, String message) {
        ChatMessage chatMessageToReturn = new ChatMessage();
        chatMessageToReturn.setNick(nick);
        chatMessageToReturn.setMessage(message);
        return chatMessageToReturn;
    }

//    static ChatMessage messageFactory(JSONObject json) throws JSONException {
//        String nick = json.getString("nick");
//        String message = json.getString("message");
//        return messageFactory(nick, message);
//    }

    public String toString() {
        return nick + ": " + message;
    }

    public String toJSON() {

        return "{\"nick\": \"" + nick + "\", \"message\": \"" + message + "\"}";
        // TODO: hacer lo del autobean.
//        AutoBean<ChatMessage> bean = AutoBeanUtils.getAutoBean(this);
//        return AutoBeanCodex.encode(bean).getPayload();
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
