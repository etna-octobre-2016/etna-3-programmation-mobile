package dev.etna.jabberclient.xmpp;

public class XMPPServiceException extends Exception
{
    public XMPPServiceException(String message)
    {
        super(message);
    }

    public XMPPServiceException(String message, Throwable wrappedThrowable)
    {
        super(message, wrappedThrowable);
    }

    public XMPPServiceException()
    {
        super();
    }
}
