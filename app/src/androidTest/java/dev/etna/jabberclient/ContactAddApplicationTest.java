package dev.etna.jabberclient;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.xmpp.XMPPService;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ContactAddApplicationTest
{
    private XMPPService service;

    @Before
    public void initialize() throws Exception
    {
        XMPPService.initXmppService("johndoe000001", "Password1", "jabber.hot-chilli.net");
        this.service = XMPPService.getInstance();
        this.service.connect();
        this.service.login();
    }

    @Test
    public void testContactAdd() throws Exception
    {
        Contact contact;

        contact = new Contact("jabber.hot-chilli.net", "mohan_a");
        this.service.addContact(contact);
        assertTrue("Should add contact", true);
    }

    @After
    public void destroy() throws Exception
    {
        this.service.logout();
    }
}