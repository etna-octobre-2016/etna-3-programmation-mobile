package dev.etna.jabberclient.tasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;

import org.jivesoftware.smack.SmackException;

import java.util.List;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.LoginActivity;
import dev.etna.jabberclient.adapters.ContactListAdapter;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.xmpp.XMPPService;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

public class ContactListFetchTask extends AsyncTask<Void, Void, XMPPServiceException>
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private List<Contact> contacts;
    private Activity activity;
    private ITaskObservable callback;
    private XMPPService service;


    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactListFetchTask(XMPPService service, Activity activity, ITaskObservable callback)
    {
        this.callback = callback;
        this.service = service;
        this.activity = activity;
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
            this.contacts = this.service.fetchContacts();
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
        ContactListAdapter adapter;
        Intent intent;
        ListView listView;
        Throwable cause;

        if (error == null)
        {
            adapter = new ContactListAdapter(this.contacts, this.activity);
            listView = (ListView) this.activity.findViewById(R.id.contactListView);
            listView.setAdapter(adapter);
            callback.onComplete();
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
                dialogBuilder.setTitle("Echec lors de la récupération des contacts");
                dialogBuilder.setMessage(error.getMessage());
                dialog = dialogBuilder.create();
                dialog.show();
            }
        }
    }
}
