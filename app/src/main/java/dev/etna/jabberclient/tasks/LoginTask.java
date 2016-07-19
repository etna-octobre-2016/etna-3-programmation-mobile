package dev.etna.jabberclient.tasks;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import dev.etna.jabberclient.LoginActivity;
import dev.etna.jabberclient.MainActivity;
import dev.etna.jabberclient.xmpp.XMPPService;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

public class LoginTask extends AsyncTask<Void, Void, XMPPServiceException>
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private LoginActivity activity;
    private XMPPService service;


    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public LoginTask(XMPPService service, LoginActivity activity)
    {
        this.service = service;
        this.activity = activity;
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
        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder;

        if (error == null)
        {
            Intent intent = new Intent(this.activity.getApplicationContext(), MainActivity.class);
            this.activity.startActivity(intent);
        }
        else
        {
            Log.e("LOGIN", error.getMessage(), error);
            dialogBuilder = new AlertDialog.Builder(this.activity);
            dialogBuilder.setTitle("Echec lors de la connexion");
            dialogBuilder.setMessage(error.getMessage());
            dialog = dialogBuilder.create();
            dialog.show();
        }
    }
}
