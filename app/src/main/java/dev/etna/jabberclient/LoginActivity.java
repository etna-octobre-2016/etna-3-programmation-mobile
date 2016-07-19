package dev.etna.jabberclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import dev.etna.jabberclient.manager.ChatManager;
import dev.etna.jabberclient.tasks.LoginTask;
import dev.etna.jabberclient.xmpp.XMPPService;

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
                self.login();
            }
        };
    }
    private void login()
    {
        JabberClientApplication app;
        String serverAddress;
        String username;
        String password;
        XMPPService xmpp;
        LoginTask task;

        serverAddress = this.serverAddressField.getText().toString();
        username = this.usernameField.getText().toString();
        password = this.passwordField.getText().toString();
        XMPPService.initXmppService(username, password, serverAddress);
        xmpp = XMPPService.getInstance();
        app = (JabberClientApplication)this.getApplication();
        app.setXmppService(xmpp);
        task = new LoginTask(xmpp, this);
        task.execute();

        ChatManager.getInstance().initAllChat();
    }
}
