package dev.etna.jabberclient.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.adapters.ContactListAdapter;
import dev.etna.jabberclient.manager.DataManager;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.tasks.ContactListFetchTask;
import dev.etna.jabberclient.tasks.ContactsDeleteTask;
import dev.etna.jabberclient.tasks.Task;

public class ChatListFragment extends ContactListFragment implements Observer {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity.setTitle(R.string.title_fragment_convers_list);
    }

    @Override
    public void onAsyncTaskComplete(Task task)
    {
        if (task instanceof ContactListFetchTask) {
            List<Contact> contacts;
            ContactListAdapter adapter;

            contacts = ((ContactListFetchTask) task).getContacts();
            contacts = sortContactByConversation(contacts);
            if (contacts.size() == 0) {
                textView.setVisibility(TextView.VISIBLE);
            } else {
                listView.setVisibility(ListView.VISIBLE);
                adapter = new ContactListAdapter(contacts, listView, activity);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(this.getItemClickListener());
                activity.invalidateOptionsMenu();
            }
        }
        else if (task instanceof ContactsDeleteTask) {
//            ContactListAdapter adapter;
//            List<Contact> contacts;
//            List<Contact> deletedContacts;
//            String message;
//            Toast toast;
//
//            adapter = (ContactListAdapter) listView.getAdapter();
//            contacts = adapter.getContacts();
//            deletedContacts = ((ContactsDeleteTask) task).getContacts();
//            contacts.removeAll(deletedContacts);
//            adapter.setContacts(contacts);
//            disableListSelection();
//            message = getString(R.string.toast_selected_deleted_count, deletedContacts.size());
//            toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
//            toast.show();
//            if (contacts.size() == 0)
//            {
//                listView.setVisibility(ListView.GONE);
//                textView.setVisibility(TextView.VISIBLE);
//            }
        }
    }

    private List<Contact> sortContactByConversation (List<Contact> contacts) {
        Cursor cursor;
        List<Contact> new_list;

        new_list = new ArrayList<Contact>();
        for (Contact c : contacts) {
            cursor = DataManager.getInstance().getMessageListByContact(c);
            if (cursor.getCount() != 0) {
                new_list.add(c);
            }
        }
        return new_list;
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
