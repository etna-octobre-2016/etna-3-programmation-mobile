package dev.etna.jabberclient.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import dev.etna.jabberclient.R;

public class ContactAddFragment extends Fragment
{
    ////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private EditText serverAddressField;
    private EditText usernameField;
    private Button submitButton;


    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactAddFragment()
    {
        // Required empty public constructor
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

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
                Log.d("test", "form submit");
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
        this.addListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_add, container, false);
    }
}
