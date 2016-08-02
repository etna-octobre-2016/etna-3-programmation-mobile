package dev.etna.jabberclient.xmpp;

import android.content.Context;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.etna.jabberclient.model.Contact;
import dev.etna.jabberclient.model.Profil;

public class XMPPService
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private static XMPPService instance = null;
    private AbstractXMPPConnection connection;
    private Context context;
    private String password;
    private String serverAddress;
    private String username;
    private VCard vcard;


    ////////////////////////////////////////////////////////////
    // STATIC METHODS
    ////////////////////////////////////////////////////////////

    public static XMPPService getInstance()
    {
        return instance;
    }
    public static void initXmppService(String username, String password, String serverAddress, Context context)
    {
        instance = new XMPPService(username, password, serverAddress, context);
    }
    public static void initXmppService(String username, String password, String serverAddress)
    {
        instance = new XMPPService(username, password, serverAddress, null);
    }

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    private XMPPService(String username, String password, String serverAddress, Context context)
    {
        this.username = username;
        this.password = password;
        this.serverAddress = serverAddress;
        this.context = context;
    }

    ////////////////////////////////////////////////////////////
    // ACCESSORS & MUTATORS
    ////////////////////////////////////////////////////////////

    public AbstractXMPPConnection getConnection()
    {
        return connection;
    }

    public String getServerAddress()
    {
        return serverAddress;
    }

    public String getUsername()
    {
        return username;
    }

    public VCard getVcard()
    {
        return vcard;
    }

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    public void addContact(Contact contact) throws XMPPServiceException
    {
        Presence subscribe;
        Roster roster;
        String jabberID;

        try
        {
            roster = Roster.getInstanceFor(this.connection);
            if (!roster.isLoaded())
            {
                roster.reloadAndWait();
            }
            jabberID = contact.getLogin();
            roster.createEntry(jabberID, contact.getUsername(), null);
            subscribe = new Presence(Presence.Type.subscribe);
            subscribe.setTo(jabberID);
            this.connection.sendStanza(subscribe);
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.CONTACT_ADD_UNEXPECTED_ERROR, this.context, e);
        }
    }
    public void connect() throws XMPPServiceException
    {
        try
        {
            this.connection = new XMPPTCPConnection(this.buildConfig());
            this.connection.connect();
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.LOGIN_UNEXPECTED_ERROR, this.context, e);
        }
    }
    public List<Contact> fetchContacts() throws XMPPServiceException
    {
        Collection<RosterEntry> entries;
        Contact contact;
        List<Contact> contacts;
        Roster roster;
        VCard contactProfile;

        try
        {
            contacts = new ArrayList<>();
            roster = Roster.getInstanceFor(this.connection);
            if (!roster.isLoaded())
            {
                roster.reloadAndWait();
            }
            entries = roster.getEntries();
            for (RosterEntry entry : entries)
            {
                contact = new Contact(entry.getUser());
                try
                {
                    contactProfile = this.getContactProfileData(contact);
                    contact.setProfile(new Profil(contactProfile));
                }
                catch (XMPPServiceException e)
                {
                    if (e.getError() != XMPPServiceError.CONTACT_PROFILE_NOT_FOUND)
                    {
                        throw e;
                    }
                    contact.setProfile(new Profil());
                }
                contacts.add(contact);
            }
            return contacts;
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.CONTACT_FETCH_UNEXPECTED_ERROR, this.context, e);
        }
    }
    public void login() throws XMPPServiceException
    {
        try
        {
            this.connection.login();
            this.vcard = new VCard();
            this.vcard.load(connection);
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.LOGIN_BAD_CREDENTIALS, this.context, e);
        }
    }
    public void logout() throws XMPPServiceException
    {
        try
        {
            this.connection.disconnect();
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.LOGOUT_UNEXPECTED_ERROR, this.context, e);
        }
    }
    public void deleteContact(Contact contact) throws XMPPServiceException
    {
        Presence unsubscribe;
        Roster roster;
        String jabberID;

        try
        {
            roster = Roster.getInstanceFor(connection);
            if (!roster.isLoaded())
            {
                roster.reloadAndWait();
            }
            jabberID = contact.getLogin();
            unsubscribe = new Presence(Presence.Type.unsubscribe);
            unsubscribe.setTo(jabberID);
            connection.sendStanza(unsubscribe);
            roster.removeEntry(roster.getEntry(jabberID));
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.CONTACT_DELETE_UNEXPECTED_ERROR, this.context, e);
        }
    }
    public void deleteContacts(List<Contact> contacts) throws XMPPServiceException
    {
        for (Contact contact : contacts)
        {
            deleteContact(contact);
        }
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private XMPPTCPConnectionConfiguration buildConfig()
    {
        XMPPTCPConnectionConfiguration config;

        config = XMPPTCPConnectionConfiguration
                .builder()
                .setServiceName(this.serverAddress)
                .setPort(5222)
                .setUsernameAndPassword(this.username, this.password)
                .build();
        return config;
    }
    private VCard getContactProfileData(Contact contact) throws XMPPServiceException
    {
        VCard vCard;
        VCardManager vCardManager;

        try
        {
            vCardManager = VCardManager.getInstanceFor(this.connection);
            vCard = vCardManager.loadVCard(contact.getLogin());
            return vCard;
        }
        catch (XMPPErrorException e)
        {
            if (e.getXMPPError().getCondition() == XMPPError.Condition.item_not_found)
            {
                throw new XMPPServiceException(XMPPServiceError.CONTACT_PROFILE_NOT_FOUND, this.context, e);
            }
            else
            {
                throw new XMPPServiceException(XMPPServiceError.CONTACT_PROFILE_UNEXPECTED_ERROR, this.context, e);
            }
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.CONTACT_PROFILE_UNEXPECTED_ERROR, this.context, e);
        }
    }
}
