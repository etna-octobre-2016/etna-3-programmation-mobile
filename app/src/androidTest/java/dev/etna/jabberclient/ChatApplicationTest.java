package dev.etna.jabberclient;

import android.support.test.runner.AndroidJUnit4;

import org.jivesoftware.smack.packet.Message;
import org.junit.Test;
import org.junit.runner.RunWith;

import dev.etna.jabberclient.manager.ChatManager;
import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.xmpp.XMPPChat;
import dev.etna.jabberclient.xmpp.XMPPService;

import static org.junit.Assert.assertTrue;

/**
 * Created by Cédric OLIVIER on 19/07/16.
 */
@RunWith(AndroidJUnit4.class)
public class ChatApplicationTest {
    private XMPPService service;
    private Contact contact;
    private XMPPChat chat;

    @Test
    public void testOpenChat() throws Exception {
        XMPPService.initXmppService("gato", "gato", "jabber.hot-chilli.eu");
        this.contact = ContactManager.getInstance().getContact("gatopreto@jabber.hot-chilli.eu");
        this.service = XMPPService.getInstance();
        this.service.connect();
        this.service.login();
        this.chat = ChatManager.getInstance().getChat(contact);
        assertTrue("Chat should be created", true);
    }

    @Test
    public void testSendMessage() throws Exception {
        Message message;

        message = new Message();
        message.setBody("Send Unit Test");
        XMPPService.initXmppService("gato", "gato", "jabber.hot-chilli.eu");
        this.contact = ContactManager.getInstance().getContact("gatopreto@jabber.hot-chilli.eu");
        this.service = XMPPService.getInstance();
        this.service.connect();
        this.service.login();

        this.chat = ChatManager.getInstance().getChat(contact);
        this.chat.sendMessage(message);
        assertTrue("The message should be sended", true);
    }

}
