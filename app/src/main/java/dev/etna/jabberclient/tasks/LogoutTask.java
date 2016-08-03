package dev.etna.jabberclient.tasks;

import android.app.Activity;

import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

public class LogoutTask extends Task
{
    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public LogoutTask(Activity activity)
    {
        super(activity, (ITaskObservable) activity);
    }

    @Override
    protected XMPPServiceException doInBackground(Void... empty)
    {
        XMPPServiceException error;

        try
        {
            this.service.logout();
            error = null;
        }
        catch (XMPPServiceException e)
        {
            error = e;
        }
        return error;
    }
}
