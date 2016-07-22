package dev.etna.jabberclient.xmpp;

import android.content.Context;
import android.content.res.Resources;

public enum XMPPServiceError
{
    LOGIN_BAD_CREDENTIALS,
    LOGIN_UNEXPECTED_ERROR,
    LOGOUT_UNEXPECTED_ERROR,
    CONTACT_ADD_UNEXPECTED_ERROR,
    CONTACT_FETCH_UNEXPECTED_ERROR;

    /**
     * Returns a localized label used to represent this enumeration value.  If no label
     * has been defined, then this defaults to the result of {@link Enum#name()}.
     *
     * <p>The name of the string resource for the label must match the name of the enumeration
     * value.  For example, for enum value 'ENUM1' the resource would be defined as 'R.string.ENUM1'.
     *
     * @param context   the context that the string resource of the label is in.
     * @return          a localized label for the enum value or the result of name()
     */
    public String getLabel(Context context)
    {
        int resourceID;
        Resources resources;
        String key;
        String label;

        if (context == null)
        {
            return this.name();
        }
        key = "error_" + this.name().toLowerCase();
        resources = context.getResources();
        resourceID = resources.getIdentifier(key, "string", context.getPackageName());
        label = (resourceID != 0) ? resources.getString(resourceID) : this.name();
        return label;
    }
}
