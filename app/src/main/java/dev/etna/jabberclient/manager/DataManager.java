package dev.etna.jabberclient.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jivesoftware.smack.packet.Message;

import dev.etna.jabberclient.interfaces.DataBaseConstants;
import dev.etna.jabberclient.model.Contact;

/**
 * Created by Cedric Olivier on 27/07/2016.
 */
public class DataManager implements DataBaseConstants{

    ////////////////////////////////////////////////////////////
    // PRIVATE STATIC ATTRIBUTES
    ////////////////////////////////////////////////////////////
    private static DataManager instance;
    private static final int VERSION_BDD    = 1;


    ////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ////////////////////////////////////////////////////////////
    private SQLiteBDD sqLiteBDD;
    private SQLiteDatabase db;


    ////////////////////////////////////////////////////////////
    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////
    private DataManager() {

    }


    ////////////////////////////////////////////////////////////
    // STATIC METHODS
    ////////////////////////////////////////////////////////////
    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }


    ////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////
    public void open() {
        db = sqLiteBDD.getWritableDatabase();
    }

    public void open(Context context) {
        sqLiteBDD = new SQLiteBDD(context, DB_NAME, null, VERSION_BDD);
        open();
    }

    public void removeAllMessage() {
        db.delete(TBL_NAME, null, null);
    }

    public long saveMessage(Message message) {
        ContentValues values = new ContentValues();

        values.put(COL_FROM, message.getFrom());
        values.put(COL_TO, message.getTo());
        values.put(COL_CONTENT, message.getBody());
        return db.insert(TBL_NAME, null, values);
    }

    public Cursor getMessageListByContact(Contact contact) {
        Cursor cursor;
        String where;

        where = COL_FROM + " = '" + contact.getLogin() + "' OR "
                + COL_TO + " = '" + contact.getLogin() + "' ";
        cursor = db.query(TBL_NAME, new String[] { COL_ID, COL_FROM, COL_TO, COL_CONTENT},
                where,
                null, null, null, null);
        return cursor;
    }
}
