package dev.etna.jabberclient.xmpp;

import android.content.Context;

public class XMPPServiceException extends Exception
{
    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public XMPPServiceException(XMPPServiceError error, Context context, Throwable wrappedThrowable)
    {
        super(error.getLabel(context), wrappedThrowable);
    }
    public XMPPServiceException(String message, Throwable wrappedThrowable)
    {
        super(message, wrappedThrowable);
    }
    public XMPPServiceException(String message)
    {
        super(message);
    }
    public XMPPServiceException()
    {
        super();
    }
}
