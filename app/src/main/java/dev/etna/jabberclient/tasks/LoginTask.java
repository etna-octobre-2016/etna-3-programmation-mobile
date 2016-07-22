package dev.etna.jabberclient.tasks;

import android.app.Activity;
import android.content.Intent;

import dev.etna.jabberclient.MainActivity;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

public class LoginTask extends Task
{
    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public LoginTask(Activity activity, ITaskObservable callback)
    {
        super(activity, callback);
    }

    @Override
    protected XMPPServiceException doInBackground(Void... empty)
    {
        XMPPServiceException error;

        try
        {
            this.service.connect();
            this.service.login();
            error = null;
        }
        catch (XMPPServiceException e)
        {
            error = e;
        }
        return error;
    }

    @Override
    protected void onPostExecute(XMPPServiceException error)
    {
        if (error == null)
        {
            Intent intent = new Intent(this.activity.getApplicationContext(), MainActivity.class);
            this.activity.startActivity(intent);
        }
        else
        {
            this.handleError(error);
        }
    }
}
