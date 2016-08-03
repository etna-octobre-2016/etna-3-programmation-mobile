package dev.etna.jabberclient;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.xmpp.XMPPService;

import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class ContactListFetchApplicationTest
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
    public void testFetch() throws Exception
    {
        List<Contact> contacts;

        contacts = this.service.fetchContacts();
        assertNotEquals("Should not be an empty contact list", 0, contacts.size());
    }

    @After
    public void destroy() throws Exception
    {
        this.service.logout();
    }
}