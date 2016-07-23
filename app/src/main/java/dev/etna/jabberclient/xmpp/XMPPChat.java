package dev.etna.jabberclient.xmpp;

import android.util.Log;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.Observable;

import dev.etna.jabberclient.model.Contact;

/**
 * Created by ceolivie on 13/07/2016.
 */
public class XMPPChat extends Observable implements ChatMessageListener{

    private Contact contact;
    private Chat chat;
    private ArrayList<Message> messageList;

    public XMPPChat(Contact contact) {
        this.contact = contact;
        try {
            openChat();
            this.messageList = new ArrayList<Message>();
        } catch (Exception e) {
            Log.i("ERR", "Connection error");
        }
    }

    private void openChat() throws Exception {
        int connectionTimer;

        XMPPConnection connection;

        connection = XMPPService.getInstance().getConnection();
        connectionTimer = 0;
        while (connection == null) {
            try {
                Thread.sleep(1000);
                connection = XMPPService.getInstance().getConnection();
            } catch (InterruptedException e) {
                Log.i("INF", "Connection test number " + connectionTimer + " failed");
            }
            if (connectionTimer++ > 10)
                throw new Exception();
        }
        ChatManager chatmanager = ChatManager.getInstanceFor(connection);
        chat = chatmanager.createChat(contact.getLogin(), this);
    }

    public void sendMessage(Message message) throws SmackException.NotConnectedException {
        chat.sendMessage(message.getBody());
        saveMessage(message);
    }

    private void receiptMessage(Message message) {
        saveMessage(message);
    }

    public ArrayList<Message> getMessageList() {
        return messageList;
    }

    private void saveMessage(Message message) {
        messageList.add(message);
        setChanged();
        notifyObservers();
    }

    public Message getLastMessage() {
        if (messageList.size() == 0)
            return null;
        return messageList.get(messageList.size() - 1);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        receiptMessage(message);
    }
}
