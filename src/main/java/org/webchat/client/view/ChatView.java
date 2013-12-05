package org.webchat.client.view;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.*;
import org.webchat.client.Messages;
import org.webchat.client.presenter.ChatPresenter;
import org.webchat.client.model.ChatMessage;
import org.webchat.client.presenter.IChatPresenter;

import java.util.List;

public class ChatView implements IChatView {
    private IChatPresenter chatPresenter;

    private String userName;
    private CellList<String> messagesList;
    private int rowCount;
    private TextBox messageBox;
    private Messages messages;
    private Label notificationsLabel;
    private ScrollPanel scrollMessagesList;

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

        scrollMessagesList = new ScrollPanel();
        scrollMessagesList.setSize("400px", "400px");
        scrollMessagesList.add(messagesList);

        messageBox = new TextBox();
        Button sendButton = new Button(messages.sendButton());
        sendButton.addClickHandler(new SendButtonHandler(this));

        textPanel.add(messageBox);
        textPanel.add(sendButton);

        mainPanel.add(scrollMessagesList);
//        mainPanel.add(messagesList);
        mainPanel.add(textPanel);

        this.notificationsLabel = new Label("Bienvenido al chat.");
        mainPanel.add(notificationsLabel);

        RootPanel.get("chatContainer").add(mainPanel);
    }

    public void addToMessageList(List<String> newMessages) {
        int lastRowCount = rowCount;
        rowCount += newMessages.size();
        messagesList.setRowCount(rowCount, true);
        messagesList.setRowData(lastRowCount, newMessages);
        scrollMessagesList.scrollToBottom();
    }

    public void onSendButtonClicked() {
        String message = messageBox.getText();
        ChatMessage messageToSend = ChatMessage.messageFactory(userName, message);
        chatPresenter.sendMessage(messageToSend);
    }

    public void setNotificationsLabelText(String text) {
        notificationsLabel.setText(text);
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
