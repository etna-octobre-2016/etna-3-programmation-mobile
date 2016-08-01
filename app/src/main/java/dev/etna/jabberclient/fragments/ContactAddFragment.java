package dev.etna.jabberclient.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.tasks.ContactAddTask;

public class ContactAddFragment extends Fragment
{
    ////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private EditText serverAddressField;
    private EditText usernameField;
    private Button submitButton;

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void addContact()
    {
        ContactAddTask task;
        ContactManager contactManager;
        Contact contact;
        String srvAdresse;
        String username;

        contactManager  = ContactManager.getInstance();
        srvAdresse      = serverAddressField.getText().toString();
        username        = usernameField.getText().toString();
        contact         = new Contact(srvAdresse, username);
        contactManager.addContact(contact);

        task = new ContactAddTask(contact, this.getActivity(), null);
        task.execute();
    }
    private void addListeners()
    {
        this.submitButton.setOnClickListener(this.getSubmitButtonClickListener());
    }
    private View.OnClickListener getSubmitButtonClickListener()
    {
        final ContactAddFragment self;

        self = this;
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                self.addContact();
            }
        };
    }

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        View view;

        super.onActivityCreated(savedInstanceState);
        view = this.getView();
        this.serverAddressField = (EditText) view.findViewById(R.id.serverAddress);
        this.usernameField = (EditText) view.findViewById(R.id.username);
        this.submitButton = (Button) view.findViewById(R.id.submit);
        getActivity().setTitle(R.string.title_fragment_contact_add);
        this.addListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_add, container, false);
    }
}
