package dev.etna.jabberclient.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jivesoftware.smack.packet.Message;

import java.util.Observable;
import java.util.Observer;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.manager.ChatManager;
import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.xmpp.XMPPChat;


public class ChatFragment extends Fragment implements Observer, View.OnClickListener{

    ////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private Button sendButton;
    private EditText editText;
    private Contact contact;
    private XMPPChat chat;
    private LinearLayout messageContener;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ChatFragment()
    {

    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void addListeners()
    {
        this.sendButton.setOnClickListener(this);
        this.contact.addObserver(this);
    }

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        this.sendButton = (Button) this.getView().findViewById(R.id.button);
        this.editText = (EditText) this.getView().findViewById(R.id.editText);
        this.messageContener = (LinearLayout) this.getView().findViewById(R.id.messageContener);
        this.contact = ContactManager.getInstance().getContact("gatopreto@jabber.hot-chilli.eu");
        this.chat = ChatManager.getInstance().getChat(contact);
        this.addListeners();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void update(Observable observable, Object o)
    {
        LinearLayout.LayoutParams lparams;
        TextView tv;

        lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //lparams.gravity = Gravity.LEFT;

        tv = new TextView(this.getView().getContext());
        tv.setLayoutParams(lparams);
        tv.setBackgroundColor(ContextCompat.getColor(this.getView().getContext(), R.color.isChat));
        tv.setPadding(10,10,0,0);
        tv.setText(contact.getLastMessage().getBody());
        messageContener.addView(tv);
    }

    @Override
    public void onClick(View view)
    {
        Message message = new Message();
        message.setBody(editText.getText().toString());
        chat.sendMessage(message);
        contact.addMessage(message);
        editText.setText(null);
    }
}
