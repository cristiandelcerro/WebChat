package org.webchat.client.view;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.*;
import org.webchat.client.Messages;
import org.webchat.client.model.IChatMessage;
import org.webchat.client.presenter.ChatPresenter;
import org.webchat.client.model.ChatMessage;
import org.webchat.client.presenter.IChatPresenter;

import java.util.LinkedList;
import java.util.List;

public class ChatView implements IChatView {
    private IChatPresenter chatPresenter;

    private String userName;
    private CellList<String> messagesList;
    private TextBox messageBox;
    private Messages messages;
    private Label notificationsLabel;
    private ScrollPanel scrollMessagesList;
    private VerticalPanel chatPanel;
    private boolean alreadyStarted;
    private LoginView loginView;
    private boolean repeatScrollDown;

    public ChatView(Messages messages, LoginView loginView) {
        this.loginView = loginView;
        this.chatPresenter = new ChatPresenter(this);
        this.messages = messages;
        this.alreadyStarted = false;
        this.repeatScrollDown = false;
    }

    public void start(String userName) {
        if (alreadyStarted) {
            chatPanel.setVisible(true);

            if (!userName.equals(this.userName)) {
                scrollMessagesList.clear();
                TextCell textCell = new TextCell();
                messagesList = new CellList<String>(textCell);
                messagesList.setPageSize(0);
                messagesList.setRowCount(0, true);
                scrollMessagesList.add(messagesList);
            }
        }

        else {
            alreadyStarted = true;
            createWidgets();
        }

        this.userName = userName;
        chatPresenter.start(userName);
    }

    public void onExitButtonClicked() {
        chatPanel.setVisible(false);
        chatPresenter.stop();
        loginView.start();
    }

    private void createWidgets() {
        chatPanel = new VerticalPanel();
        HorizontalPanel textPanel = new HorizontalPanel();

        TextCell textCell = new TextCell();
        messagesList = new CellList<String>(textCell);
        messagesList.setPageSize(0);
        messagesList.setRowCount(0, true);

        scrollMessagesList = new ScrollPanel();
        scrollMessagesList.setStyleName("scrollPanel");
        scrollMessagesList.add(messagesList);

        messageBox = new TextBox();
        messageBox.addStyleName("textBoxChat");
        Button sendButton = new Button(messages.sendButton());
        sendButton.addClickHandler(new SendButtonHandler(this));

        textPanel.add(messageBox);
        textPanel.add(sendButton);

        chatPanel.add(scrollMessagesList);
        chatPanel.add(textPanel);

        this.notificationsLabel = new Label(messages.welcomeMessage());
        chatPanel.add(notificationsLabel);

        chatPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        Button exitButton = new Button(messages.exitButton());
        exitButton.addClickHandler(new ExitButtonHandler(this));
        chatPanel.add(exitButton);

        RootPanel.get("chatContainer").add(chatPanel);
        scrollMessagesList.scrollToBottom();
    }

    public void addToMessageList(List<IChatMessage> newMessages) {
        if (newMessages.isEmpty()) {
            // Lo de bajar el scroll lo hace antes de introducir los mensajes, así que ponemos esto para que lo
            // repita una vez más.
            if (repeatScrollDown) {
                repeatScrollDown = false;
                scrollMessagesList.setVerticalScrollPosition(scrollMessagesList.getMaximumVerticalScrollPosition());
            }

            return;
        }


        LinkedList<String> newMessagesInStrings = convertToStrings(newMessages);
        int rowCount = messagesList.getRowCount();
        messagesList.setPageSize(rowCount + newMessages.size());
        messagesList.setRowCount(rowCount + newMessages.size(), true);
        messagesList.setRowData(rowCount, newMessagesInStrings);
        scrollMessagesList.setVerticalScrollPosition(scrollMessagesList.getMaximumVerticalScrollPosition());
        repeatScrollDown = true;
    }

    private LinkedList<String> convertToStrings(List<IChatMessage> newMessages) {
        LinkedList<String> listToReturn = new LinkedList<String>();
        for (IChatMessage message: newMessages)
            listToReturn.add(message.getNick() + ": " + message.getMessage());
        return listToReturn;
    }

    public void onSendButtonClicked() {
        String message = messageBox.getText();
        messageBox.setText("");
        ChatMessage messageToSend = new ChatMessage(userName, message);
        chatPresenter.sendMessage(messageToSend);
    }

    public void notifyFailedReception() {
        notificationsLabel.setText(messages.failedReception());
    }

    public void notifyFailedSending() {
        notificationsLabel.setText(messages.failedSending());
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

    class ExitButtonHandler implements ClickHandler {
        private ChatView chatView;

        public ExitButtonHandler(ChatView chatView) {
            this.chatView = chatView;
        }

        public void onClick(ClickEvent event) {
            chatView.onExitButtonClicked();
        }
    }
}
