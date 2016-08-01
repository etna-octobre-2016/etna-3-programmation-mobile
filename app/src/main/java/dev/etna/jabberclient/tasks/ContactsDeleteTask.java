package dev.etna.jabberclient.tasks;

import android.app.Activity;

import java.util.List;

import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.model.Contact;

public class ContactsDeleteTask extends Task
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private List<Contact> contacts;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactsDeleteTask(List<Contact> contacts, Activity activity, ITaskObservable callback)
    {
        super(activity, callback);
        this.contacts = contacts;
    }

    ////////////////////////////////////////////////////////////
    // ACCESSORS & MUTATORS
    ////////////////////////////////////////////////////////////

    public List<Contact> getContacts()
    {
        return contacts;
    }

    ////////////////////////////////////////////////////////////
    // PROTECTED METHODS
    ////////////////////////////////////////////////////////////

    @Override
    protected Throwable doInBackground(Void... voids)
    {
        Exception error;

        try
        {
            service.deleteContacts(contacts);
            error = null;
        }
        catch (Exception e)
        {
            error = e;
        }
        return error;
    }
}
