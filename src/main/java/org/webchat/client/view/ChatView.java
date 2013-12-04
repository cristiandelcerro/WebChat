package org.webchat.client.view;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.*;
import org.webchat.client.Messages;
import org.webchat.client.presenter.ChatPresenter;
import org.webchat.client.util.ChatMessage;

import java.util.List;

public class ChatView {
    private ChatPresenter chatPresenter;

    private String userName;
    private CellList<String> messagesList;
    private int rowCount;
    private TextBox messageBox;
    private Messages messages;

    public ChatView(String userName, Messages messages) {
        this.userName = userName;
        this.chatPresenter = new ChatPresenter(userName, this);
        this.messages = messages;
    }

    public void start() {
        VerticalPanel mainPanel = new VerticalPanel();
        HorizontalPanel textPanel = new HorizontalPanel();

        TextCell textCell = new TextCell();
        messagesList = new CellList<String>(textCell);
        rowCount = 0;
        messagesList.setRowCount(0, true);

//        ScrollPanel scrollMessagesList = new ScrollPanel();
//        scrollMessagesList.add(messagesList);

        messageBox = new TextBox();
        Button sendButton = new Button(messages.sendButton());
        sendButton.addClickHandler(new SendButtonHandler(this));

        textPanel.add(messageBox);
        textPanel.add(sendButton);

//        mainPanel.add(scrollMessagesList);
        mainPanel.add(messagesList);
        mainPanel.add(textPanel);

        RootPanel.get("chatContainer").add(mainPanel);
    }

    //TODO: parece que no se escribe bien el mensaje.
    public void addToMessageList(List<String> newMessages) {
        int lastRowCount = rowCount;
        rowCount += newMessages.size();
        messagesList.setRowCount(rowCount, true);
        messagesList.setRowData(lastRowCount, newMessages);
    }

    public void onSendButtonClicked() {
        String message = messageBox.getText();
        ChatMessage messageToSend = ChatMessage.messageFactory(userName, message);
        chatPresenter.sendMessage(messageToSend);
    }

    class SendButtonHandler implements ClickHandler {
        private ChatView chatView;

        public SendButtonHandler(ChatView chatView) {
            this.chatView = chatView;
        }

        public void onClick(ClickEvent event) {
            chatView.onSendButtonClicked();
        }
    }
}
