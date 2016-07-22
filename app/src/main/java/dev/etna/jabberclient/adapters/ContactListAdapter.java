package dev.etna.jabberclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.model.Contact;

public class ContactListAdapter extends BaseAdapter
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private Context context;
    private LayoutInflater layoutInflater;
    private List list;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactListAdapter(List list, Context context)
    {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    @Override
    public int getCount()
    {
        return this.list.size();
    }

    @Override
    public Object getItem(int i)
    {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup root)
    {
        Contact contact;
        TextView usernameText;

        if (view == null)
        {
            view = layoutInflater.inflate(R.layout.fragment_contact_list_item, root, false);
        }
        contact = (Contact)this.getItem(i);
        usernameText = (TextView)view.findViewById(R.id.username);
        usernameText.setText(contact.getUsername());
        return view;
    }
}
