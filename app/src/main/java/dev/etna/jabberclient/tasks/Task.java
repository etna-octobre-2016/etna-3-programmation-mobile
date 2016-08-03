package dev.etna.jabberclient.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import org.jivesoftware.smack.SmackException;

import dev.etna.jabberclient.MainActivity;
import dev.etna.jabberclient.R;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.xmpp.XMPPService;

abstract public class Task extends AsyncTask<Void, Void, Throwable>
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    protected Activity activity;
    protected ITaskObservable callback;
    protected XMPPService service;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public Task(Activity activity, ITaskObservable callback)
    {
        this.activity = activity;
        this.callback = callback;
        this.service = XMPPService.getInstance();
    }

    ////////////////////////////////////////////////////////////
    // PROTECTED METHODS
    ////////////////////////////////////////////////////////////

    @Override
    protected void onPostExecute(Throwable error)
    {
        if (error == null)
        {
            try
            {
                callback.onAsyncTaskComplete(this);
            }
            catch (Exception e)
            {
                handleError(e);
            }
        }
        else
        {
            handleError(error);
        }
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    protected void handleError(Throwable error)
    {
        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder;
        MainActivity activity;
        Throwable cause;

        cause = error.getCause();
        if (cause instanceof SmackException.NotLoggedInException || cause instanceof SmackException.NotConnectedException)
        {
            activity = (MainActivity) this.activity;
            activity.logout();
        }
        else
        {
            dialogBuilder = new AlertDialog.Builder(this.activity);
            dialogBuilder.setTitle(R.string.error_title);
            dialogBuilder.setMessage(error.getMessage());
            dialog = dialogBuilder.create();
            dialog.show();
        }
    }
}
