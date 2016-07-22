package dev.etna.jabberclient.tasks;

import android.app.Activity;
import android.widget.ListView;

import java.util.List;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.adapters.ContactListAdapter;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

public class ContactListFetchTask extends Task
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private List<Contact> contacts;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactListFetchTask(Activity activity, ITaskObservable callback)
    {
        super(activity, callback);
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
        ContactListAdapter adapter;
        ListView listView;

        if (error == null)
        {
            adapter = new ContactListAdapter(this.contacts, this.activity);
            listView = (ListView) this.activity.findViewById(R.id.contactListView);
            listView.setAdapter(adapter);
            callback.onComplete();
        }
        else
        {
            this.handleError(error);
        }
    }
}
