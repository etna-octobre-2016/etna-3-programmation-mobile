package dev.etna.jabberclient.model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ceolivie on 13/07/2016.
 */
public class ContactManager {
    private static ContactManager instance = null;
    private ArrayList<ContactModel> contact_list;
    private ContactModel contact;
    public static final String EXTRA_CONTACT = "extra.contact";

    private ContactManager() {
        contact_list = new ArrayList<ContactModel>();
        initContactList();
    }

    public static ContactManager getInstance() {
        if (instance == null)
            instance = new ContactManager();
        return instance;
    }

    public void initContactList() {
        //TODO: get all users contact with api or saved data
        addContact("gatopreto@jabber.hot-chilli.eu");
    }

    public void addContact(String login, String nom) {
        contact = new ContactModel(login, nom);
        addContact(contact);
    }

    public void addContact(String login) {
        contact = new ContactModel(login);
        addContact(contact);
    }

    private void addContact(ContactModel contactModel) {
        if (contact.getLogin().equals(getContact(contact.getLogin()).getLogin()))
            Log.i("WAR", "Contact « " + contact.getLogin() + " » already exist in contact list");
        else
            contact_list.add(contact);
    }

    public void removeContact(String login) {
        contact = getContact(login);
        contact_list.remove(contact);
    }

    public ContactModel getContact(String login) {
        for (ContactModel contactModel : contact_list) {
            if (contactModel.getLogin().equals(login))
                return contactModel;
        }
        Log.i("WAR", "Contact not found : " + login );
        return null;
    }
}
