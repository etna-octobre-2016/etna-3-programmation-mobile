package dev.etna.jabberclient.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jivesoftware.smack.packet.Message;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.RunnableFuture;

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
    private TextView tv;
    private Contact contact;
    private XMPPChat chat;
    private LinearLayout messageContener;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ChatFragment()
    {
        // Required empty public constructor
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

    RelativeLayout rl;
    @Override
    public void update(Observable observable, Object o)
    {
        rl = new RelativeLayout(this.getActivity());

        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        tv = new TextView(this.getView().getContext());

        if (contact.getLastMessage().getFrom() != null)
        {
            lprams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tv.setBackgroundColor(ContextCompat.getColor(this.getView().getContext(), R.color.isChat));
        }
        else
        {
            lprams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tv.setBackgroundColor(ContextCompat.getColor(this.getView().getContext(), R.color.myChat));
        }

        tv.setText(contact.getLastMessage().getBody());
        rl.addView(tv, lprams);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageContener.addView(rl);
            }
        });

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
