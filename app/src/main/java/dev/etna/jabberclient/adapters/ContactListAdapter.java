package dev.etna.jabberclient.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.etna.jabberclient.R;
import dev.etna.jabberclient.model.Contact;

public class ContactListAdapter extends BaseAdapter
{
    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    ////////////////////////////////////////////////////////////

    private AbsListView listView;
    private Context context;
    private LayoutInflater layoutInflater;
    private List contacts;
    private int selectedContactsCount;

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////

    public ContactListAdapter(List contacts, AbsListView listView, Context context)
    {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contacts = contacts;
        this.listView = listView;
        this.selectedContactsCount = 0;
        sortContactsByUsername();
    }

    ////////////////////////////////////////////////////////////
    // ACCESSORS & MUTATORS
    ////////////////////////////////////////////////////////////

    public List<Contact> getContacts()
    {
        return contacts;
    }

    public void setContacts(List<Contact> contacts)
    {
        this.contacts = contacts;
    }

    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////

    @Override
    public int getCount()
    {
        return contacts.size();
    }

    @Override
    public Object getItem(int i)
    {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    public List<Contact> getSelectedContacts()
    {
        int i;
        int size;
        int index;
        ArrayList<Contact> selectedContacts;
        SparseBooleanArray selectedIndexes;

        selectedContacts = new ArrayList<>();
        selectedIndexes = listView.getCheckedItemPositions();
        size = selectedIndexes.size();
        for (i = 0; i < size; i++)
        {
            index = selectedIndexes.keyAt(i);
            if (selectedIndexes.get(index))
            {
                selectedContacts.add((Contact) getItem(index));
            }
        }
        return selectedContacts;
    }

    public int getSelectedContactsCount()
    {
        int count;
        int i;
        int index;
        int size;
        SparseBooleanArray selectedIndexes;

        count = 0;
        selectedIndexes = listView.getCheckedItemPositions();
        size = selectedIndexes.size();
        for (i = 0; i < size; i++)
        {
            index = selectedIndexes.keyAt(i);
            if (selectedIndexes.get(index))
            {
                count++;
            }
        }
        return count;
    }

    @Override
    public View getView(int i, View view, ViewGroup parentView)
    {
        byte[] avatarBinary;
        int checkboxVisibility;
        Bitmap bitmap;
        BitmapFactory.Options bitmapOptions;
        CheckBox checkBox;
        Contact contact;
        ImageView avatar;
        TextView username;
        AbsListView listView;

        if (view == null)
        {
            view = layoutInflater.inflate(R.layout.fragment_contact_list_item, parentView, false);
        }
        listView = (AbsListView) parentView;
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        checkboxVisibility = (listView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) ? CheckBox.VISIBLE : CheckBox.GONE;
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
        if (checkboxVisibility == CheckBox.VISIBLE)
        {
            checkBox.setOnCheckedChangeListener(this.getSelectionCheckboxListener(i));
        }
        return view;
    }

    public void sortContactsByUsername()
    {
        Collections.sort(contacts, new Comparator<Contact>()
        {
            @Override
            public int compare(Contact contact1, Contact contact2)
            {
                return contact1.getUsername().compareTo(contact2.getUsername());
            }
        });
    }

    ////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ////////////////////////////////////////////////////////////

    private CompoundButton.OnCheckedChangeListener getSelectionCheckboxListener(final int itemIndex)
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton checkBox, boolean checked)
            {
                String message;
                Toast toast;

                listView.setItemChecked(itemIndex, checked);
                selectedContactsCount = getSelectedContactsCount();
                if (selectedContactsCount == 0)
                {
                    message = context.getString(R.string.toast_selected_contacts_none);
                }
                else
                {
                    message = context.getString(R.string.toast_selected_contacts_count, selectedContactsCount);
                }
                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();
                updateOptionsMenu();
            }
        };
    }

    private void updateOptionsMenu()
    {
        ((Activity) context).invalidateOptionsMenu();
    }
}
