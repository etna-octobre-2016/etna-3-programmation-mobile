package dev.etna.jabberclient.manager;

import android.util.Log;

import java.util.ArrayList;

import dev.etna.jabberclient.model.Contact;

/**
 * Created by ceolivie on 13/07/2016.
 */
public class ContactManager {
    private static ContactManager instance = null;
    private ArrayList<Contact> contact_list;
    public static final String EXTRA_CONTACT = "extra.contact";

    private ContactManager() {
        Contact contact;

        contact_list = new ArrayList<Contact>();
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
        Contact contact;

        contact = new Contact(login, nom);
        addContact(contact);
    }

    public void addContact(String login) {
        Contact contact;

        contact = new Contact(login);
        addContact(contact);
    }

    private void addContact(Contact contact) {
        if (getContact(contact.getLogin()) != null)
            Log.i("WAR", "Contact « " + contact.getLogin() + " » already exist in contact list");
        else
            contact_list.add(contact);
    }

    public void removeContact(String login) {
        Contact contact;

        contact = getContact(login);
        contact_list.remove(contact);
    }

    public Contact getContact(String login) {
        for (Contact contactModel : contact_list) {
            if (contactModel.getLogin().equals(login))
                return contactModel;
        }
        Log.i("WAR", "Contact not found : " + login );
        return null;
    }

    public ArrayList<Contact> getContact_list() {
        return contact_list;
    }
}
