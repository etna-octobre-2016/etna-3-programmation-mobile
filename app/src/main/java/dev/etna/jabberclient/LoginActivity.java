package dev.etna.jabberclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import dev.etna.jabberclient.interfaces.ITaskObservable;
import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.tasks.LoginTask;
import dev.etna.jabberclient.tasks.Task;
import dev.etna.jabberclient.xmpp.XMPPService;

public class LoginActivity extends AppCompatActivity implements ITaskObservable
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
    public void onAsyncTaskComplete(Task task)
    {
        if (task instanceof LoginTask)
        {
            Contact userContact;
            Intent intent;
            LoginTask loginTask;

            loginTask = (LoginTask) task;
            userContact = new Contact(loginTask.getServerAddress(), loginTask.getUsername());
            ContactManager.getInstance().setMainUser(userContact);
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onContentChanged()
    {
        super.onContentChanged();
        initialize();
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
        submitButton.setOnClickListener(getSubmitButtonClickListener());
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
                resetFormErrors();
                if (isFormValid())
                {
                    self.login();
                }
            }
        };
    }

    private void initialize()
    {
        serverAddressField = (EditText) findViewById(R.id.serverAddress);
        usernameField = (EditText) findViewById(R.id.username);
        passwordField = (EditText) findViewById(R.id.password);
        submitButton = (Button) findViewById(R.id.submit);
        addListeners();
    }

    private boolean isFormValid()
    {
        boolean isValid;
        String password;
        String serverAddress;
        String username;

        isValid = true;
        serverAddress = serverAddressField.getText().toString();
        username = usernameField.getText().toString();
        password = passwordField.getText().toString();

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
         * Username validation
         */
        if (TextUtils.isEmpty(username))
        {
            usernameField.setError(getText(R.string.error_field_required));
            isValid = false;
        }

        /*
         * Password validation
         */
        if (TextUtils.isEmpty(password))
        {
            passwordField.setError(getText(R.string.error_field_required));
            isValid = false;
        }

        return isValid;
    }

    private void login()
    {
        LoginTask task;
        String serverAddress;
        String password;
        String username;

        serverAddress = serverAddressField.getText().toString();
        password = passwordField.getText().toString();
        username = usernameField.getText().toString();
        XMPPService.initXmppService(username, password, serverAddress, this);
        task = new LoginTask(this);
        task.execute();
    }

    private void resetFormErrors()
    {
        passwordField.setError(null);
        serverAddressField.setError(null);
        usernameField.setError(null);
    }

}
