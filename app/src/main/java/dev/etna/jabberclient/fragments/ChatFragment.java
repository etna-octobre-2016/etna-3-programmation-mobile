package dev.etna.jabberclient.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;

import java.util.Observable;
import java.util.Observer;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.interfaces.ITaskObservable;
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
    private LinearLayout messageContener;
    private RelativeLayout rl;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ChatFragment() {
        // Required empty public constructor
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private void addListeners() {
        this.sendButton.setOnClickListener(this);
        this.chat.addObserver(this);
    }

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.sendButton         = (Button) this.getView().findViewById(R.id.button);
        this.editText           = (EditText) this.getView().findViewById(R.id.editText);
        this.messageContener    = (LinearLayout) this.getView().findViewById(R.id.messageContener);
        this.contact            = ContactManager.getInstance().getCurrentChatContact();
        this.chat               = ChatManager.getInstance().getChat(contact);
        this.addListeners();
        restorSavedConversation();
    }

    RelativeLayout.LayoutParams lprams;

    public void restorSavedConversation() {

        DataManager dm;
        Cursor cursor;
        Message message;
        String mainUserLogin;
        ContactManager cm;

        cm = ContactManager.getInstance();
        mainUserLogin = cm.getMainUser().getLogin();


        dm = DataManager.getInstance();
        System.out.println(" +++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ");
        cursor = dm.getMessageListByContact(this.contact);
//        rl = new RelativeLayout(this.getView().getContext());
////                getAc.getActivity());
//        tv = new TextView(this.getView().getContext());



        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            System.out.println("======================================");
            rl = new RelativeLayout(this.getView().getContext());
            tv = new TextView(this.getView().getContext());
            message = new Message();
            message.setFrom(cursor.getString(1));
            message.setTo(cursor.getString(2));
            message.setBody(cursor.getString(3));
            System.out.println(message.getFrom() + " -> " + message.getTo() + " = " + message.getBody());

            lprams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            if (message.getFrom() == null || !message.getFrom().equals(mainUserLogin)) {
                lprams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                tv.setBackgroundColor(ContextCompat.getColor(this.getView().getContext(), R.color.isChat));
            } else {
                lprams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                tv.setBackgroundColor(ContextCompat.getColor(this.getView().getContext(), R.color.myChat));
            }
            tv.setText(message.getBody());

            rl.addView(tv, lprams);
            messageContener.addView(rl);
        }
        System.out.println("//////////////////////////////////////////////////////////////");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    private View formatView() {

        View view = new View(this.getActivity());

        FrameLayout frameLayout         = new FrameLayout(view.getContext());
        RelativeLayout relativeLayout   = new RelativeLayout(view.getContext());
        Button sendButton               = new Button(view.getContext());
        sendButton.setText("Send");

        relativeLayout.addView(sendButton);
        frameLayout.addView(relativeLayout);

        this.getActivity().addContentView(frameLayout, null);
        return this.getView();
    }

    @Override
    public void update(Observable observable, Object o)
    {
        String mainUserLogin;

        mainUserLogin = ContactManager.getInstance().getMainUser().getLogin();
        rl = new RelativeLayout(this.getActivity());

        RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tv = new TextView(this.getView().getContext());
        if (!chat.getLastMessage().getFrom().equals(mainUserLogin)) {
            lprams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tv.setBackgroundColor(ContextCompat.getColor(this.getView().getContext(), R.color.isChat));
        } else {
            lprams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tv.setBackgroundColor(ContextCompat.getColor(this.getView().getContext(), R.color.myChat));
        }

        tv.setText(chat.getLastMessage().getBody());
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
        try {
            chat.sendMessage(message);
        } catch (SmackException.NotConnectedException e) {
            Log.i("ERR", "Error Delivering block");
        }
        editText.setText(null);
    }
}
