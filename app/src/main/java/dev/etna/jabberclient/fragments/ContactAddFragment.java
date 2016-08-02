package dev.etna.jabberclient.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.tasks.ContactAddTask;
import dev.etna.jabberclient.tasks.Task;

public class ContactAddFragment extends Fragment implements ITaskObservable
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

        task = new ContactAddTask(contact, getActivity(), this);
        task.execute();
    }

    private void addListeners()
    {
        submitButton.setOnClickListener(getSubmitButtonClickListener());
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
                resetFormErrors();
                if (isFormValid())
                {
                    self.addContact();
                }
            }
        };
    }

    private void initialize()
    {
        View view;

        view = getView();
        serverAddressField = (EditText) view.findViewById(R.id.serverAddress);
        usernameField = (EditText) view.findViewById(R.id.username);
        submitButton = (Button) view.findViewById(R.id.submit);
        getActivity().setTitle(R.string.title_fragment_contact_add);
        addListeners();
    }

    private boolean isFormValid()
    {
        boolean isValid;
        String serverAddress;
        String username;

        isValid = true;
        serverAddress = serverAddressField.getText().toString();
        username = usernameField.getText().toString();

        /*
         * Server address validation
         */
        if (TextUtils.isEmpty(serverAddress))
        {
            serverAddressField.setError(getText(R.string.error_field_required));
            isValid = false;
        }
        else if (!Patterns.DOMAIN_NAME.matcher(serverAddress).matches())
        {
            serverAddressField.setError(getText(R.string.error_field_invalid));
            isValid = false;
        }

        /*
         * Server address validation
         */
        if (TextUtils.isEmpty(username))
        {
            usernameField.setError(getText(R.string.error_field_required));
            isValid = false;
        }

        return isValid;
    }

    private void resetFormErrors()
    {
        serverAddressField.setError(null);
        usernameField.setError(null);
    }

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
    public void onAsyncTaskComplete(Task task)
    {
        if (task instanceof ContactAddTask)
        {
            String message;
            Toast toast;

            message = getString(R.string.toast_contact_added_successfully);
            toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
            toast.show();
            usernameField.setText(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_add, container, false);
    }
}
