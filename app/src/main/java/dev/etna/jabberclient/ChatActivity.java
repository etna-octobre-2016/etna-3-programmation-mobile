package dev.etna.jabberclient;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jivesoftware.smack.packet.Message;

import java.util.Observable;
import java.util.Observer;

import dev.etna.jabberclient.model.ContactManager;
import dev.etna.jabberclient.model.ContactModel;
import dev.etna.jabberclient.xmpp.XMPPChat;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    private Button bSend;
    private ContactModel contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        contact = ContactManager.getInstance().getContact(
                getIntent().getStringExtra(ContactManager.EXTRA_CONTACT));
        contact.addObserver(this);
        bSend = (Button) findViewById(R.id.button);
        bSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView text = (TextView) findViewById(R.id.editText);
        Message message = new Message();
        message.setBody(text.toString());
        contact.addMessage(message);
    }

    @Override
    public void update(Observable observable, Object o) {
        TextView tv;
        LinearLayout messageContener;
        LinearLayout.LayoutParams lparams;

        messageContener = (LinearLayout) findViewById(R.id.messageContener);
        lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //lparams.gravity = Gravity.LEFT;

        tv = new TextView(this);
        tv.setLayoutParams(lparams);
        tv.setBackgroundColor(ContextCompat.getColor(this, R.color.isChat));
        tv.setPadding(10,10,0,0);
        tv.setText(contact.getLastMessage().getBody());
        messageContener.addView(tv);
    }
}
