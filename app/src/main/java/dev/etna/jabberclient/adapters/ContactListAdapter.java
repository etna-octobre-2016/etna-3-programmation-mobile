package dev.etna.jabberclient.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.model.Contact;

public class ContactListAdapter extends BaseAdapter
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private boolean isSelectionEnabled;
    private LayoutInflater layoutInflater;
    private List list;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactListAdapter(List list, Context context)
    {
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        isSelectionEnabled = false;
    }

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    public void enableSelection()
    {
        isSelectionEnabled = true;
        notifyDataSetChanged();
    }

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
        byte[] avatarBinary;
        int checkboxVisibility;
        Bitmap bitmap;
        BitmapFactory.Options bitmapOptions;
        CheckBox checkBox;
        Contact contact;
        ImageView avatar;
        TextView username;

        if (view == null)
        {
            view = layoutInflater.inflate(R.layout.fragment_contact_list_item, root, false);
        }
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        checkboxVisibility = (isSelectionEnabled) ? CheckBox.VISIBLE : CheckBox.GONE;
        checkBox.setVisibility(checkboxVisibility);
        contact = (Contact)this.getItem(i);
        avatarBinary = contact.getAvatar();
        bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.outHeight = R.dimen.img_avatar_size;
        bitmapOptions.outWidth = R.dimen.img_avatar_size;
        bitmap = BitmapFactory.decodeByteArray(avatarBinary, 0, avatarBinary.length, bitmapOptions);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        avatar.setImageBitmap(bitmap);
        username = (TextView) view.findViewById(R.id.username);
        username.setText(contact.getUsername());
        return view;
    }
}
