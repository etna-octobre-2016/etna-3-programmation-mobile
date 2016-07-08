package dev.etna.jabberclient.xmpp;

import android.util.Log;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import dev.etna.jabberclient.profil.Profil;

public class XMPPService
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private AbstractXMPPConnection connection;
    private String password;
    private String serverAddress;
    private String username;
    private VCard vcard;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    /**
     * Initializes connection to XMPP server
     * @param username
     * @param password
     * @param serverAddress
     * @throws XMPPServiceException
     */
    public XMPPService(String username, String password, String serverAddress)
    {
        this.username = username;
        this.password = password;
        this.serverAddress = serverAddress;
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
    public void login() throws XMPPServiceException
    {
        try
        {
            this.connection.login();
            this.vcard = new VCard();
            this.vcard.load(connection);
            Profil profil = new Profil(this);
            Log.i(" ###### profil --",(String) profil.toString());
        }
        catch (Exception e)
        {
            throw new XMPPServiceException(XMPPServiceError.LOGIN_BAD_CREDENTIALS.toString(), e);
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

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    public AbstractXMPPConnection getConnection() {
        return connection;
    }

    public VCard getVcard() {
        return vcard;
    }


}
