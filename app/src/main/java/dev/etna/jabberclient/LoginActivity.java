package dev.etna.jabberclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity
{
    ////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private EditText serverAddressField;
    private EditText usernameField;
    private EditText passwordField;
    private Button submitButton;

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    @Override
    public void onContentChanged()
    {
        super.onContentChanged();
        this.serverAddressField = (EditText) this.findViewById(R.id.serverAddress);
        this.usernameField = (EditText) this.findViewById(R.id.username);
        this.passwordField = (EditText) this.findViewById(R.id.password);
        this.submitButton = (Button) this.findViewById(R.id.submit);
        this.addListeners();
    }

    ////////////////////////////////////////////////////////////
    // PROTECTED METHODS
    ////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void addListeners()
    {
        this.submitButton.setOnClickListener(this.getSubmitButtonClickListener());
    }
    private OnClickListener getSubmitButtonClickListener()
    {
        final LoginActivity self;

        self = this;
        return new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.d("server address = ", self.serverAddressField.getText().toString());
                Log.d("username = ", self.usernameField.getText().toString());
                Log.d("password = ", self.passwordField.getText().toString());
            }
        };
    }
}
