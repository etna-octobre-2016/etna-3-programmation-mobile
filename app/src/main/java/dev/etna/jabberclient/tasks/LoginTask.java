package dev.etna.jabberclient.tasks;

import android.app.Activity;

import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.model.Profil;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

public class LoginTask extends Task
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private String serverAddress;
    private String username;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public LoginTask(Activity activity)
    {
        super(activity, (ITaskObservable) activity);
        this.serverAddress = service.getServerAddress();
        this.username = service.getUsername();
    }

    ////////////////////////////////////////////////////////////
    // ACCESSORS
    ////////////////////////////////////////////////////////////

    public String getServerAddress()
    {
        return serverAddress;
    }

    public String getUsername()
    {
        return username;
    }

    ////////////////////////////////////////////////////////////
    // PROTECTED METHODS
    ////////////////////////////////////////////////////////////

    @Override
    protected XMPPServiceException doInBackground(Void... empty)
    {
        XMPPServiceException error;

        try
        {
            this.service.connect();
            this.service.login();
            new Profil(service);
            error = null;
        }
        catch (XMPPServiceException e)
        {
            error = e;
        }
        return error;
    }
}
