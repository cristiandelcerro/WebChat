package org.webchat.client.model;

import java.util.List;

public interface IServerResponse {
    public int getNextSeq();
    public void setNextSeq(int nextSeq);
    public List<IChatMessage> getMessages();
    public void setMessages(List<IChatMessage> messages);
}
