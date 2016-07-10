package dev.etna.jabberclient.xmpp;

public enum XMPPServiceError
{
    LOGIN_BAD_CREDENTIALS ("Ces identifiants sont incorrects"),
    LOGIN_UNEXPECTED_ERROR ("Une erreur inattendue a eu lieu lors de la connexion"),
    LOGOUT_UNEXPECTED_ERROR ("Une erreur inattendue a eu lieu lors de la déconnexion");

    private String name = "";

    XMPPServiceError(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }
}
