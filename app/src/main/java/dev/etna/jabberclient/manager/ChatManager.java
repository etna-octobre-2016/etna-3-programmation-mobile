package dev.etna.jabberclient.manager;

import java.util.ArrayList;
import java.util.HashMap;

import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.xmpp.XMPPChat;

/**
 * Created by Cedric Olivier on 14/07/16.
 */
public class ChatManager {

    ///////////////////////////////////////////////////////////////////////////
    //  STATIC ATTRIBUTS
    ///////////////////////////////////////////////////////////////////////////
    private static ChatManager instance         = null;


    ///////////////////////////////////////////////////////////////////////////
    //  PRIVATE ATTRIBUTS
    ///////////////////////////////////////////////////////////////////////////
    private HashMap<Contact, XMPPChat> chatMap  = null;


    ///////////////////////////////////////////////////////////////////////////
    //  CONSTRUCTOR
    ///////////////////////////////////////////////////////////////////////////

    private ChatManager() {
        chatMap     = new HashMap<Contact, XMPPChat>();
        initAllChat();
    }

    ///////////////////////////////////////////////////////////////////////////
    //  PUBLIC METHODS
    ///////////////////////////////////////////////////////////////////////////

    public static ChatManager getInstance() {
        if (instance == null)
            instance = new ChatManager();
        return instance;
    }

    public void initAllChat () {
        ArrayList<Contact> list;

        list = ContactManager.getInstance().getContact_list();
        for (Contact contact : list) {
            initChat(contact);
        }
    }

    public void initChat(Contact contact) {
        XMPPChat chat;

        if (chatMap.get(contact) == null) {
            chat = new XMPPChat(contact);
            chatMap.put(contact, chat);
        }
    }

    public XMPPChat getChat(Contact contact) {
        if (chatMap.get(contact) == null) {
            initChat(contact);
        }
        return chatMap.get(contact);
    }
}
