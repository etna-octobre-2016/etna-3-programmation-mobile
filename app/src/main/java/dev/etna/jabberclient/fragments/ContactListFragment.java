package dev.etna.jabberclient.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    // CONSTANTS
    ////////////////////////////////////////////////////////////

    public static final int OPTIONS_MENU_DEFAULT = 0;
    public static final int OPTIONS_MENU_SELECT = 1;

    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private int optionsMenuMode = OPTIONS_MENU_DEFAULT;
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
        else if (itemID == R.id.action_contact_list_cancel)
        {
            disableListSelection();
            return true;
        }
        else if (itemID == R.id.action_contact_list_delete)
        {
            deleteSelectedItems();
            return true;
        }
        return false;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        if (optionsMenuMode == OPTIONS_MENU_DEFAULT)
        {
            menu.findItem(R.id.action_contact_list_select).setVisible(true);
            menu.findItem(R.id.action_contact_list_cancel).setVisible(false);
            menu.findItem(R.id.action_contact_list_delete).setVisible(false);
        }
        else if (optionsMenuMode == OPTIONS_MENU_SELECT)
        {
            menu.findItem(R.id.action_contact_list_select).setVisible(false);
            menu.findItem(R.id.action_contact_list_cancel).setVisible(true);
            menu.findItem(R.id.action_contact_list_delete).setVisible(true);
        }
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void addListeners()
    {
        listView.setOnItemClickListener(this.getItemClickListener());
    }

    private void deleteSelectedItems()
    {
        final AlertDialog dialog;
        AlertDialog.Builder dialogBuilder;
        ContactListAdapter adapter;
        int count;

        adapter = (ContactListAdapter) listView.getAdapter();
        count = adapter.getSelectedContactsCount();
        if (count > 0)
        {
            dialogBuilder = new AlertDialog.Builder(activity);
            dialogBuilder.setMessage(getString(R.string.dialog_contacts_delete_confirmation, count));
            dialogBuilder.setPositiveButton(R.string.label_action_delete, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    Log.i("CONTACTS-DELETE", "go!");
                    dialogInterface.dismiss();
                }
            });
            dialogBuilder.setNegativeButton(R.string.label_action_cancel, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    Log.i("CONTACTS-DELETE", "canceled");
                    dialogInterface.cancel();
                }
            });
            dialog = dialogBuilder.create();
            dialog.show();
        }
    }

    private void disableListSelection()
    {
        listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
        listView.invalidateViews();
        optionsMenuMode = OPTIONS_MENU_DEFAULT;
        activity.invalidateOptionsMenu();  // @NOTE: this line triggers the call of onPrepareOptionsMenu()
    }

    private void enableListSelection()
    {
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.invalidateViews();
        optionsMenuMode = OPTIONS_MENU_SELECT;
        activity.invalidateOptionsMenu(); // @NOTE: this line triggers the call of onPrepareOptionsMenu()
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
