package dev.etna.jabberclient.xmpp;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.etna.jabberclient.model.Contact;

public class XMPPService
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private static XMPPService instance = null;
    private AbstractXMPPConnection connection;
    private String password;
    private String serverAddress;
    private String username;


    ////////////////////////////////////////////////////////////
    // STATIC METHODES
    ////////////////////////////////////////////////////////////

    public static XMPPService getInstance() {
        return instance;
    }

    public static void initXmppService(String username, String password, String serverAddress) {
        instance = new XMPPService(username, password, serverAddress);
    }

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    /**
     * Initializes connection to XMPP server
     * @param username the username
     * @param password the password
     * @param serverAddress the server address
     */
    private XMPPService(String username, String password, String serverAddress)
    {
        this.username = username;
        this.password = password;
        this.serverAddress = serverAddress;
    }
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
            throw new XMPPServiceException(XMPPServiceError.CONTACT_ADD_UNEXPECTED_ERROR.toString(), e);
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
            throw new XMPPServiceException(XMPPServiceError.LOGIN_UNEXPECTED_ERROR.toString(), e);
        }
    }
    public List<Contact> fetchContacts() throws XMPPServiceException
    {
        Collection<RosterEntry> entries;
        List<Contact> contacts;
        Roster roster;

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
                contacts.add(new Contact(entry.getUser()));
            }
            return contacts;
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.CONTACT_FETCH_UNEXPECTED_ERROR.toString(), e);
        }
    }
    public void login() throws XMPPServiceException
    {
        try
        {
            this.connection.login();
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.LOGIN_BAD_CREDENTIALS.toString(), e);
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
            throw new XMPPServiceException(XMPPServiceError.LOGOUT_UNEXPECTED_ERROR.toString(), e);
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

    public XMPPConnection getConnection() {
        return this.connection;
    }
}
