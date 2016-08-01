package dev.etna.jabberclient.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import java.util.ArrayList;

import dev.etna.jabberclient.MainActivity;
import dev.etna.jabberclient.R;
import dev.etna.jabberclient.adapters.ContactListAdapter;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.tasks.ContactListFetchTask;
import dev.etna.jabberclient.tasks.ContactsDeleteTask;
import dev.etna.jabberclient.tasks.Task;

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
    private TextView textView;

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initialize();
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
    public void onAsyncTaskComplete(Task task)
    {
        if (task instanceof ContactListFetchTask)
        {
            List<Contact> contacts;
            ContactListAdapter adapter;

            contacts = ((ContactListFetchTask) task).getContacts();
            if (contacts.size() == 0)
            {
                textView.setVisibility(TextView.VISIBLE);
            }
            else
            {
                listView.setVisibility(ListView.VISIBLE);
                adapter = new ContactListAdapter(contacts, listView, activity);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(this.getItemClickListener());
                activity.invalidateOptionsMenu();
            }
        }
        else if (task instanceof ContactsDeleteTask)
        {
            ContactListAdapter adapter;
            List<Contact> contacts;
            List<Contact> deletedContacts;
            String message;
            Toast toast;

            adapter = (ContactListAdapter) listView.getAdapter();
            contacts = adapter.getContacts();
            deletedContacts = ((ContactsDeleteTask) task).getContacts();
            contacts.removeAll(deletedContacts);
            adapter.setContacts(contacts);
            disableListSelection();
            message = getString(R.string.toast_selected_deleted_count, deletedContacts.size());
            toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
            toast.show();
            if (contacts.size() == 0)
            {
                listView.setVisibility(ListView.GONE);
                textView.setVisibility(TextView.VISIBLE);
            }
        }
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
        ContactListAdapter contactListAdapter;
        Drawable deleteIcon;
        Drawable selectIcon;
        MenuItem cancelMenuItem;
        MenuItem deleteMenuItem;
        MenuItem selectMenuItem;

        contactListAdapter = (ContactListAdapter) listView.getAdapter();
        cancelMenuItem = menu.findItem(R.id.action_contact_list_cancel);
        deleteMenuItem = menu.findItem(R.id.action_contact_list_delete);
        selectMenuItem = menu.findItem(R.id.action_contact_list_select);
        if (optionsMenuMode == OPTIONS_MENU_DEFAULT)
        {
            selectMenuItem.setVisible(true);
            cancelMenuItem.setVisible(false);
            deleteMenuItem.setVisible(false);
            selectIcon = selectMenuItem.getIcon();
            if (contactListAdapter == null) // @NOTE: contactListAdapter is NULL before contacts fetching
            {
                selectIcon.setAlpha(128);
                selectMenuItem.setEnabled(false);
            }
            else
            {
                if (contactListAdapter.getCount() == 0)
                {
                    selectMenuItem.setEnabled(false);
                    selectIcon.setAlpha(128);
                }
                else
                {
                    selectMenuItem.setEnabled(true);
                    selectIcon.setAlpha(255);
                }
            }
        }
        else if (optionsMenuMode == OPTIONS_MENU_SELECT)
        {
            selectMenuItem.setVisible(false);
            cancelMenuItem.setVisible(true);
            deleteMenuItem.setVisible(true);
            deleteIcon = deleteMenuItem.getIcon();
            if (contactListAdapter.getSelectedContactsCount() == 0)
            {
                deleteMenuItem.setEnabled(false);
                deleteIcon.setAlpha(128);
            }
            else
            {
                deleteMenuItem.setEnabled(true);
                deleteIcon.setAlpha(255);
            }
        }
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void deleteSelectedItems()
    {
        AlertDialog dialog;
        AlertDialog.Builder dialogBuilder;
        int count;
        final ContactListAdapter adapter;
        final ITaskObservable self;

        adapter = (ContactListAdapter) listView.getAdapter();
        count = adapter.getSelectedContactsCount();
        if (count > 0)
        {
            self = this;
            dialogBuilder = new AlertDialog.Builder(activity);
            dialogBuilder.setMessage(getString(R.string.dialog_contacts_delete_confirmation, count));
            dialogBuilder.setPositiveButton(R.string.label_action_delete, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    Task task;

                    task = new ContactsDeleteTask(adapter.getSelectedContacts(), activity, self);
                    task.execute();
                }
            });
            dialogBuilder.setNegativeButton(R.string.label_action_cancel, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
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
                ContactManager contactManager;

                contact         = (Contact) adapterView.getItemAtPosition(index);
                contactManager  = ContactManager.getInstance();
                contactManager.setCurrentChat(contact);
                MainActivity.getInstance().switchFragment(new ChatFragment());
                contact = (Contact) adapterView.getItemAtPosition(index);
            }
        };
    }

    private void initialize()
    {
        ContactListFetchTask task;

        activity = getActivity();
        listView = (ListView) activity.findViewById(R.id.contactListView);
        textView = (TextView) activity.findViewById(R.id.contactListMessage);
        activity.setTitle(R.string.title_fragment_contact_list);
        task = new ContactListFetchTask(activity, this);
        task.execute();
    }
}
