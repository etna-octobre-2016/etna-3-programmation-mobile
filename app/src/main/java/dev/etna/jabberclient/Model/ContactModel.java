package dev.etna.jabberclient.model;

import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by ceolivie on 13/07/2016.
 */
public class ContactModel extends Observable {
    private String login;
    private String name;
    private ArrayList<Message> mList;

    public ContactModel(String login) {
        this.login = login;
        mList = new ArrayList<Message>();
    }

    public ContactModel(String login, String name) {
        this.login = login;
        this.name = name;
        mList = new ArrayList<Message>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Message> getMessageList() {
        return mList;
    }

    public void setModelList(ArrayList<Message> mList) {
        this.mList = mList;
    }

    public void addMessage(Message message) {
        mList.add(message);
        setChanged();
        notifyObservers();
    }

    public Message getLastMessage() {
        return mList.get(mList.size() - 1);
    }
}
