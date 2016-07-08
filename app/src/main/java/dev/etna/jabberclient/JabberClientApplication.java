package dev.etna.jabberclient;

import android.app.Application;

import dev.etna.jabberclient.xmpp.XMPPService;

public class JabberClientApplication extends Application
{
    private XMPPService xmppService;

    ////////////////////////////////////////////////////////////////////////////
    // ACCESSORS AND MUTATORS
    ////////////////////////////////////////////////////////////////////////////

    public XMPPService getXmppService()
    {
        return xmppService;
    }
    public void setXmppService(XMPPService xmppService)
    {
        this.xmppService = xmppService;
    }
}
