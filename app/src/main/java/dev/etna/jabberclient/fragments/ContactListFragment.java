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

import dev.etna.jabberclient.MainActivity;
import dev.etna.jabberclient.R;
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
            ((MainActivity) activity).setOptionsMenuMode(MainActivity.OPTIONS_MENU_CONTACT_LIST_SELECT);
            activity.invalidateOptionsMenu(); // @NOTE: this line triggers the call of activity.onPrepareOptionsMenu
            return true;
        }
        else if (itemID == R.id.action_contact_list_cancel)
        {
            disableListSelection();
            ((MainActivity) activity).setOptionsMenuMode(MainActivity.OPTIONS_MENU_CONTACT_LIST_DEFAULT);
            activity.invalidateOptionsMenu();
        }
        else if (itemID == R.id.action_contact_list_delete)
        {
            Log.i("CONTACT-LIST", "delete");
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

    private void disableListSelection()
    {
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        listView.invalidateViews();
    }

    private void enableListSelection()
    {
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.invalidateViews();
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
