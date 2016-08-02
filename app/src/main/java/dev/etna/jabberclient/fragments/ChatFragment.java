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


public class ChatFragment extends Fragment
        implements Observer, View.OnClickListener {

    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ///////////////////////////////////////////////////////////////////////////

    private Button sendButton;
    private EditText editText;
    private TextView textView;
    private Contact contact;
    private XMPPChat chat;
    private LinearLayout chatLayout;
    private RelativeLayout relativeLayout;
    private String mainLogin;
    private RelativeLayout.LayoutParams layoutParams;


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        View view;

        view            = this.getView();
        this.sendButton = (Button) view.findViewById(R.id.button);
        this.editText   = (EditText) view.findViewById(R.id.editText);
        this.chatLayout = (LinearLayout) view.findViewById(R.id.chatLayout);
        this.contact    = ContactManager.getInstance().getCurrentChatContact();
        this.chat       = ChatManager.getInstance().getChat(contact);
        this.mainLogin  = ContactManager.getInstance().getMainUser().getLogin();

        addListeners();
        getActivity().setTitle(contact.getUsername());
        loadPreviousConversation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void update(Observable observable, Object o) {
        Cursor cursor;

        cursor = DataManager.getInstance().getMessageListByContact(contact);
        cursor.moveToLast();
        updateChatView(getMessage(cursor));
    }

    @Override
    public void onClick(View view)
    {
        Message message = new Message();
        message.setBody(editText.getText().toString());
        try {
            chat.sendMessage(message);
        } catch (SmackException.NotConnectedException e) {
            Log.i("ERR", "Error Delivering block");
        }
        editText.setText(null);
    }


    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////

    private void updateChatView(Message message) {
        Context context;

        context         = this.getView().getContext();
        relativeLayout  = new RelativeLayout(context);
        textView        = new TextView(context);
        layoutParams    = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        formatTextViewChat(message.getTo().equals(mainLogin));
        textView.setText(message.getBody());
        relativeLayout.addView(textView, layoutParams);
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatLayout.addView(relativeLayout);
            }
        });
    }

    private void formatTextViewChat(boolean isMainUser) {
        Context context;
        int color;

        context = this.getView().getContext();
        if (isMainUser) {
            color = ContextCompat.getColor(context, R.color.isChat);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            color = ContextCompat.getColor(context, R.color.myChat);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        textView.setBackgroundColor(color);
    }

    private void addListeners() {
        this.sendButton.setOnClickListener(this);
        this.chat.addObserver(this);
    }

    private Message getMessage(Cursor cursor) {
        Message message;

        message = new Message();
        message.setFrom(cursor.getString(1));
        message.setTo(cursor.getString(2));
        message.setBody(cursor.getString(3));
        return message;
    }

    private void loadPreviousConversation() {
        Cursor cursor;

        cursor = DataManager.getInstance().getMessageListByContact(contact);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            updateChatView(getMessage(cursor));
        }
    }
}
