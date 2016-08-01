package dev.etna.jabberclient.xmpp;

import android.util.Log;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.Observable;

import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.manager.DataManager;
import dev.etna.jabberclient.model.Contact;

/**
 * Created by Cedric Olivier on 13/07/2016.
 */
public class XMPPChat extends Observable implements ChatMessageListener{

    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTS
    ///////////////////////////////////////////////////////////////////////////

    private DataManager dataManager;
    private Contact contact;
    private Chat chat;


    ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ///////////////////////////////////////////////////////////////////////////

    public XMPPChat(Contact contact) {
        this.contact = contact;
        try {
            openChat();
            dataManager = DataManager.getInstance();
        } catch (Exception e) {
            Log.i("ERR", "Connection error");
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ///////////////////////////////////////////////////////////////////////////

    public void sendMessage(Message message)
            throws SmackException.NotConnectedException {
        message.setFrom(ContactManager.getInstance().getMainUser().getLogin());
        message.setTo(contact.getLogin());
        saveMessage(message);
        chat.sendMessage(message);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        receiptMessage(message);
    }


    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////

    private void openChat() throws Exception {
        int connectionTimer;
        ChatManager chatManager;
        XMPPConnection connection;

        connection      = XMPPService.getInstance().getConnection();
        connectionTimer = 0;

        while (connection == null) {
            try {
                Thread.sleep(500);
                connection = XMPPService.getInstance().getConnection();
            } catch (InterruptedException e) {
                Log.i("Chat connection", "Connection test number " + connectionTimer
                        + " failed");
            }
            if (connectionTimer++ > 10)
                throw new Exception();
        }
        chatManager = ChatManager.getInstanceFor(connection);
        this.chat   = chatManager.createChat(contact.getLogin(), this);
    }

    private void receiptMessage(Message message) {
        String from;

        from = message.getFrom();
        from = from.split("/")[0];
        message.setFrom(from);
        saveMessage(message);
    }

    private void saveMessage(Message message) {
        dataManager.saveMessage(message);
        setChanged();
        notifyObservers();
    }
}
