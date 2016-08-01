package dev.etna.jabberclient.fragments;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Message;

import java.util.Observable;
import java.util.Observer;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.manager.ChatManager;
import dev.etna.jabberclient.manager.ContactManager;
import dev.etna.jabberclient.manager.DataManager;
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
    private LinearLayout chatLayout;
    private RelativeLayout rl;
    private RelativeLayout.LayoutParams layoutParams;
    private String mainUserLogin;


    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ChatFragment() {
        // Required empty public constructor
    }


    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.sendButton     = (Button) this.getView().findViewById(R.id.button);
        this.editText       = (EditText) this.getView().findViewById(R.id.editText);
        this.chatLayout     = (LinearLayout) this.getView().findViewById(R.id.messageContener);
        this.contact        = ContactManager.getInstance().getCurrentChatContact();
        this.chat           = ChatManager.getInstance().getChat(contact);
        this.mainUserLogin  = ContactManager.getInstance().getMainUser().getLogin();
        this.addListeners();
        restorePreviousConversation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void update(Observable observable, Object o) {
        Cursor cursor;

        cursor = DataManager.getInstance().getMessageListByContact(this.contact);
        cursor.moveToLast();
        updateConversationView(formatMessage(cursor));
    }

    @Override
    public void onClick(View view) {
        Message message;

        message = new Message();
        message.setBody(editText.getText().toString());
        try {
            chat.sendMessage(message);
        } catch (SmackException.NotConnectedException e) {
            Log.i("ERR", "Error Delivering block");
        }
        editText.setText(null);
    }


    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void updateConversationView(Message message) {
        Context context;

        context = this.getView().getContext();
        rl      = new RelativeLayout(context);
        tv      = new TextView(context);
        layoutParams = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        if (message.getTo().equals(mainUserLogin)) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tv.setBackgroundColor(ContextCompat.getColor(context, R.color.isChat));
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tv.setBackgroundColor(ContextCompat.getColor(context, R.color.myChat));
        }
        tv.setText(message.getBody());
        rl.addView(tv, layoutParams);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatLayout.addView(rl);
            }
        });
    }

    private void restorePreviousConversation() {
        Cursor cursor;

        cursor  = DataManager.getInstance().getMessageListByContact(this.contact);
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            updateConversationView(formatMessage(cursor));
        }
    }

    private void addListeners() {
        this.sendButton.setOnClickListener(this);
        this.chat.addObserver(this);
    }

    private Message formatMessage (Cursor cursor) {
        Message message;

        message = new Message();
        message.setFrom(cursor.getString(1));
        message.setTo(cursor.getString(2));
        message.setBody(cursor.getString(3));
        return message;
    }
}
