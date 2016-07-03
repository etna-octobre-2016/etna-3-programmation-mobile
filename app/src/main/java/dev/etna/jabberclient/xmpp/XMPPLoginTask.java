package dev.etna.jabberclient.xmpp;

import android.os.AsyncTask;
import android.util.Log;

public class XMPPLoginTask extends AsyncTask<Void, Void, XMPPServiceException>
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private XMPPService service;


    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public XMPPLoginTask(XMPPService service)
    {
        this.service = service;
    }

    @Override
    protected XMPPServiceException doInBackground(Void... empty)
    {
        XMPPServiceException error;

        try
        {
            this.service.connect();
            this.service.login();
            error = null;
        }
        catch (XMPPServiceException e)
        {
            error = e;
        }
        return error;
    }

    @Override
    protected void onPostExecute(XMPPServiceException error)
    {
        if (error == null)
        {
            Log.i("LOGIN", "success, no error");
        }
        else
        {
            Log.e("LOGIN", "error: " + error.getMessage());
        }
    }
}
