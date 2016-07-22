package dev.etna.jabberclient.tasks;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import dev.etna.jabberclient.LoginActivity;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

public class LogoutTask extends Task
{
    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public LogoutTask(Activity activity, ITaskObservable callback)
    {
        super(activity, callback);
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

    @Override
    protected void onPostExecute(XMPPServiceException error)
    {
        if (error == null)
        {
            Intent intent = new Intent(this.activity.getApplicationContext(), LoginActivity.class);
            this.activity.startActivity(intent);
        }
        else
        {
            this.handleError(error);
        }
    }
}
