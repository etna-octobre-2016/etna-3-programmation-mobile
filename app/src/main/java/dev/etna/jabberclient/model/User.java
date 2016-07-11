package dev.etna.jabberclient.model;

public class User
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private String serverAddress;
    private String username;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public User()
    {
    }
    public User(String serverAddress, String username)
    {
        this.serverAddress = serverAddress;
        this.username = username;
    }

    ////////////////////////////////////////////////////////////
    // ACCESSORS AND MUTATORS
    ////////////////////////////////////////////////////////////

    public String getServerAddress()
    {
        return serverAddress;
    }
    public String getUsername()
    {
        return username;
    }
    public void setServerAddress(String serverAddress)
    {
        this.serverAddress = serverAddress;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
}
