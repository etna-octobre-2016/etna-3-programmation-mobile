package dev.etna.jabberclient.model;

/**
 * Created by ceolivie on 13/07/2016.
 */
public class Contact
{
    private String login;
    private String serverAddress;
    private String username;

    public Contact(String login)
    {
        String[] arr;

        arr = login.split("@");
        this.setUsername(arr[0]);
        this.setServerAddress(arr[1]);
        this.setLogin(login);
    }

    public Contact (String serverAddress, String username)
    {
        this.setLogin(username + "@" + serverAddress);
        this.setServerAddress(serverAddress);
        this.setUsername(username);
    }

    public String getLogin()
    {
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
}
