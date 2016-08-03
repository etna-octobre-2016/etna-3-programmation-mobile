package dev.etna.jabberclient.tasks;

import android.app.Activity;

import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.manager.ContactManager;
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
            service.addContact(contact);
            ContactManager.getInstance().addContact(contact);
            error = null;
        }
        catch (XMPPServiceException e)
        {
            error = e;
        }
        return error;
    }
}
