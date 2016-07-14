package dev.etna.jabberclient.manager;

import java.util.ArrayList;
import java.util.HashMap;

import dev.etna.jabberclient.model.ContactModel;
import dev.etna.jabberclient.xmpp.XMPPChat;

/**
 * Created by Gato on 14/07/16.
 */
public class ChatManager {
    private static ChatManager instance             = null;
    private HashMap<ContactModel, XMPPChat> chatMap = null;
    private ChatManager() {
        chatMap     = new HashMap<ContactModel, XMPPChat>();
    }

    public static ChatManager getInstance() {
        if (instance == null)
            instance = new ChatManager();
        return instance;
    }

    public void initAllChat () {
        XMPPChat chat;
        ArrayList<ContactModel> list;

        list = ContactManager.getInstance().getContact_list();
        for (ContactModel contact : list) {
            initChat(contact);
        }
    }
    public void initChat(ContactModel contact) {
        if (chatMap.get(contact) == null) {
            XMPPChat chat = new XMPPChat(contact);
            chatMap.put(contact, chat);
        }
    }

    public XMPPChat getChat(ContactModel contact) {
        if (chatMap.get(contact) == null) {
            initChat(contact);
        }
        return chatMap.get(contact);
    }

}
