package dev.etna.jabberclient;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import dev.etna.jabberclient.xmpp.XMPPService;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserLoginApplicationTest
{
    private XMPPService service;

    @Test
    public void testLogin() throws Exception
    {
        XMPPService.initXmppService("johndoe000001", "Password1", "jabber.hot-chilli.net");
        this.service = XMPPService.getInstance();
        this.service.connect();
        this.service.login();
        this.service.logout();
        assertTrue("Should be logged", true);
    }

    @Test
    public void testLoginWithBadCredentials() throws Exception
    {
        try
        {
            XMPPService.initXmppService("johndoe000001", "Password1", "jabber.hot-chilli.net");
            this.service = XMPPService.getInstance();
            this.service.connect();
            this.service.login();
            this.service.logout();
            assertTrue("Should not be logged", false);
        }
        catch (XMPPServiceException e)
        {
            assertTrue("Should not be logged", true);
        }
    }
}