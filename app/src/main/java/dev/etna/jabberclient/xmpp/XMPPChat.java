package dev.etna.jabberclient.xmpp;

import android.util.Log;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import dev.etna.jabberclient.model.ContactModel;

/**
 * Created by ceolivie on 13/07/2016.
 */
public class XMPPChat implements ChatMessageListener{

    private ContactModel contact;
    private Chat chat;
    public XMPPChat(ContactModel contact) {
        this.contact = contact;
        openChat();
    }

    private void openChat() {
        ChatManager chatmanager = ChatManager.getInstanceFor(XMPPService.getInstance().getConnection());
        chat = chatmanager.createChat(contact.getLogin(), this);
    }

    public void sendMessage(Message message) {
        try {
            chat.sendMessage(message.getBody());
        } catch (SmackException.NotConnectedException e) {
            Log.i("ERR", "Error Delivering block");
        }
    }

    private void receiptMessage(Message message) {
        System.out.println("Received message: " + message);
//        contact.addMessage(message);
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        receiptMessage(message);
    }
}
