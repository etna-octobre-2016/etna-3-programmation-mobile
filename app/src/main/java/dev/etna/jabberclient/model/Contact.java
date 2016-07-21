package dev.etna.jabberclient.model;

import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by ceolivie on 13/07/2016.
 */
public class Contact {
    private String login;
    private String username;
    private String serverAddress;

    public Contact(String login) {
        this.login = login;
    }

    public Contact (String serverAddress, String username)
    {
        this.serverAddress = serverAddress;
        this.username = username;
        this.login = username + "@" + serverAddress;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = username;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
}
