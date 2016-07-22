package dev.etna.jabberclient.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.etna.jabberclient.JabberClientApplication;
import dev.etna.jabberclient.MainActivity;
import dev.etna.jabberclient.R;
import dev.etna.jabberclient.tasks.ContactListFetchTask;

public class ContactListFragment extends Fragment
{
    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactListFragment()
    {
        // Required empty public constructor
    }

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

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void initialize()
    {
        ContactListFetchTask task;
        JabberClientApplication app;

        app = (JabberClientApplication)this.getActivity().getApplication();
        task = new ContactListFetchTask(app.getXmppService(), (MainActivity) this.getActivity());
        task.execute();
    }
}
