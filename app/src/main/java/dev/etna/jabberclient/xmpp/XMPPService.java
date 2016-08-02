package dev.etna.jabberclient.xmpp;

import android.content.Context;

import org.jivesoftware.smack.AbstractXMPPConnection;
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
    private Profil myProfil;


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

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    public void addContact(String contactUsername, String contactServerAddress) throws XMPPServiceException
    {
        Roster roster;
        String jabberID;

        try
        {
            roster = Roster.getInstanceFor(this.connection);
            if (!roster.isLoaded())
            {
                roster.reloadAndWait();
            }
            jabberID = contactUsername + "@" + contactServerAddress;
            roster.createEntry(jabberID, contactUsername, null);
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.CONTACT_ADD_UNEXPECTED_ERROR, this.context, e);
        }
    }
    public void addContact(Contact contact) throws XMPPServiceException
    {
        this.addContact(contact.getUsername(), contact.getServerAddress());
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
                contactProfile = this.getContactProfileData(contact);
                contact.setAvatar(contactProfile.getAvatar());
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
            myProfil = new Profil(this);
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
    private VCard getContactProfileData(Contact contact) throws Exception
    {
        VCardManager vCardManager;

        vCardManager = VCardManager.getInstanceFor(this.connection);
        return vCardManager.loadVCard(contact.getLogin());
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////
    public AbstractXMPPConnection getConnection() {
        return connection;
    }

    public VCard getVcard() {
        return vcard;
    }
    public Profil getMyProfil() {return myProfil;}

}
