package dev.etna.jabberclient.tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.jivesoftware.smack.SmackException;

import dev.etna.jabberclient.LoginActivity;
import dev.etna.jabberclient.MainActivity;
import dev.etna.jabberclient.model.User;
import dev.etna.jabberclient.xmpp.XMPPService;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

public class ContactAddTask extends AsyncTask<Void, Void, XMPPServiceException>
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private MainActivity activity;
    private XMPPService service;
    private User user;


    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactAddTask(User user, XMPPService service, MainActivity activity)
    {
        this.service = service;
        this.activity = activity;
        this.user = user;
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE / PROTECTED
    ////////////////////////////////////////////////////////////

    @Override
    protected XMPPServiceException doInBackground(Void... empty)
    {
        XMPPServiceException error;

        try
        {
            this.service.addContact(this.user);
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
        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder;
        Intent intent;
        Throwable cause;

        if (error == null)
        {
            Log.i("CONTACT_ADD", "contact added successfully!");
        }
        else
        {
            cause = error.getCause();
            if (cause instanceof SmackException.NotLoggedInException || cause instanceof SmackException.NotConnectedException)
            {
                intent = new Intent(this.activity.getApplicationContext(), LoginActivity.class);
                this.activity.startActivity(intent);
            }
            else
            {
                dialogBuilder = new AlertDialog.Builder(this.activity);
                dialogBuilder.setTitle("Echec lors de l'ajout d'un contact");
                dialogBuilder.setMessage(error.getMessage());
                dialog = dialogBuilder.create();
                dialog.show();
            }
        }
    }
}
