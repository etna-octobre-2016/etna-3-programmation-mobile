package dev.etna.jabberclient.model;

/**
 * Created by ceolivie on 13/07/2016.
 */
public class Contact
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private Profil profile;
    private String login;
    private String serverAddress;
    private String username;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public Contact()
    {
        this.setProfile(null);
        this.setLogin(null);
        this.setServerAddress(null);
        this.setUsername(null);
    }

    public Contact(String login) throws IllegalArgumentException
    {
        String[] arr;

        if (login.indexOf('@') == -1)
        {
            throw new IllegalArgumentException("Invalid login format. Should be formated like so: username@server.tld. Given: " + login);
        }
        arr = login.split("@");
        this.setProfile(null);
        this.setUsername(arr[0]);
        this.setServerAddress(arr[1]);
        this.setLogin(login);
    }

    public Contact(String serverAddress, String username)
    {
        this.setProfile(null);
        this.setLogin(username + "@" + serverAddress);
        this.setServerAddress(serverAddress);
        this.setUsername(username);
    }

    ////////////////////////////////////////////////////////////
    // ACCESSORS & MUTATORS
    ////////////////////////////////////////////////////////////

    public String getLogin()
    {
        if (login == null) {
            login =  username + "@" + serverAddress;
        }
        return this.login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getServerAddress()
    {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress)
    {
        this.serverAddress = serverAddress;
    }

    public byte[] getAvatar() {

        return profile.getAvatar();
    }

    public void setAvatar(byte[] avatar) {

        profile.setAvatar(avatar);
    }

    public Profil getProfile() {

        return profile;
    }

    public void setProfile(Profil profile) {

        this.profile = profile;
    }

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    public boolean hasAvatar() {

        return (profile.hasAvatar());
    }
}
