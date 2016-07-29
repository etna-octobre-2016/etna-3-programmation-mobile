package dev.etna.jabberclient.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.adapters.ContactListAdapter;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.tasks.ContactListFetchTask;

public class ContactListFragment extends Fragment implements ITaskObservable
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private Activity activity;
    private ListView listView;

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
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.contact_list_fragment, menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemID;

        itemID = item.getItemId();
        if (itemID == R.id.action_contact_list_select)
        {
            enableListSelection();
            return true;
        }
        return false;
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void addListeners()
    {
        listView.setOnItemClickListener(this.getItemClickListener());
    }

    private void enableListSelection()
    {
        ContactListAdapter adapter;

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = (ContactListAdapter) listView.getAdapter();
        adapter.enableSelection();
    }

    private AdapterView.OnItemClickListener getItemClickListener()
    {
        return new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long id)
            {
                Contact contact;

                contact = (Contact) adapterView.getItemAtPosition(index);
                Log.i("CONTACT-LIST", "contact selected : " + contact.getUsername());
            }
        };
    }

    private void initialize()
    {
        ContactListFetchTask task;

        activity = getActivity();
        listView = (ListView) activity.findViewById(R.id.contactListView);
        task = new ContactListFetchTask(activity, this);
        task.execute();
    }
}
