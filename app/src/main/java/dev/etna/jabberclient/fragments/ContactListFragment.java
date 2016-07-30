package dev.etna.jabberclient.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import dev.etna.jabberclient.MainActivity;
import dev.etna.jabberclient.R;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.tasks.ContactListFetchTask;

public class ContactListFragment extends Fragment implements ITaskObservable
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private Activity activity;

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onComplete()
    {
        this.addListeners();
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void addListeners()
    {
        ListView listView;

        listView = (ListView) this.activity.findViewById(R.id.contactListView);
        listView.setOnItemClickListener(this.getItemClickListener());
    }

    private AdapterView.OnItemClickListener getItemClickListener()
    {
        return new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id)
            {
                Contact contact;
                ContactManager contactManager;

                contact         = (Contact) adapterView.getItemAtPosition(index);
                contactManager  = ContactManager.getInstance();
                contactManager.setCurrentChat(contact);
                MainActivity.getInstance().switchFragment(new ChatFragment());
            }
        };
    }

    private void initialize()
    {
        ContactListFetchTask task;

        this.activity = this.getActivity();
        task = new ContactListFetchTask(this.activity, this);
        task.execute();
    }
}
