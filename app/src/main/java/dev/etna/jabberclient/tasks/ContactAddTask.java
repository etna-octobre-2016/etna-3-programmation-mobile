package dev.etna.jabberclient.tasks;

import android.app.Activity;
import android.util.Log;

import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.xmpp.XMPPServiceException;

public class ContactAddTask extends Task
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private Contact contact;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactAddTask(Contact contact, Activity activity, ITaskObservable callback)
    {
        super(activity, callback);
        this.contact = contact;
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
            this.service.addContact(this.contact);
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
            Log.i("CONTACT_ADD", "contact added successfully!");
        }
        else
        {
            this.handleError(error);
        }
    }
}
