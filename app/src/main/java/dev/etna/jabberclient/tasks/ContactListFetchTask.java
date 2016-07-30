package dev.etna.jabberclient.tasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.adapters.ContactListAdapter;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.utils.Drawables;

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
    protected Throwable doInBackground(Void... empty)
    {
        Exception error;
        ContactManager contactManager;

        try
        {
            this.contacts = this.service.fetchContacts();
            contactManager = ContactManager.getInstance();
            synchronized (contactManager) {
                contactManager.setContactsList((ArrayList) contacts);
            }
            setDefaultAvatars();
            error = null;
        }
        catch (Exception e)
        {
            error = e;
        }
        return error;
    }

    @Override
    protected void onPostExecute(Throwable error)
    {
        ContactListAdapter adapter;
        ListView listView;

        if (error == null)
        {
            try
            {
                adapter = new ContactListAdapter(contacts, activity);
                listView = (ListView) activity.findViewById(R.id.contactListView);
                listView.setAdapter(adapter);
                callback.onComplete();
            }
            catch (Exception e)
            {
                handleError(e);
            }
        }
        else
        {
            this.handleError(error);
        }
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void setDefaultAvatars() throws Exception
    {
        int i;
        int size;
        Contact contact;

        size = contacts.size();
        for (i = 0; i < size; i++)
        {
            contact = contacts.get(i);
            if (!contact.hasAvatar())
            {
                contact.setAvatar(Drawables.getDrawableAsByteArray(activity, R.drawable.avatar_default, Bitmap.CompressFormat.PNG));
            }
        }
    }
}
