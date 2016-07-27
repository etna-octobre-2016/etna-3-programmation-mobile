package dev.etna.jabberclient.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;

import dev.etna.jabberclient.model.Contact;

/**
 * Created by Cedric Olivier on 27/07/2016.
 */
public class DataManager {
    private static final int VERSION_BDD    = 1;
    public static final String TBL_NAME     = "MESSAGE_HISTORY";
    private final String COL_ID             = "ID";
    private final String COL_FROM           = "FROM_USER";
    private final String COL_TO             = "TO_USER";
    private final String COL_CONTENT        = "MESSAGE_CONTENT";

    private Context context;
    private SQLiteBDD sqLiteBDD;
    private SQLiteDatabase db;

    public DataManager(Context context) throws Exception {
        sqLiteBDD = new SQLiteBDD(context, SQLiteBDD.DB_NAME, null, VERSION_BDD);
    }

    public void open() {
        db = sqLiteBDD.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDB() {
        return db;
    }

    public void removeAllMessage() {
        db.delete(TBL_NAME, null, null);
    }

    public void updateTable() {
        sqLiteBDD.onUpgrade(db, 1, 2);
    }

    public long saveMessage(Message message) {
        ContentValues values = new ContentValues();

        values.put(COL_FROM, message.getFrom());
        values.put(COL_TO, message.getTo());
        values.put(COL_CONTENT, message.getBody());
        return db.insert(TBL_NAME, null, values);
    }

    public int removeMessageWhitID(int id) {
        return db.delete(TBL_NAME, COL_ID + " = " + id, null);
    }

    public Cursor getMessageListByContact(Contact contact) {
        Cursor cursor;
        String where;

        where = COL_FROM + " = '" + contact.getLogin() + "' OR " + COL_TO + " = '" +contact.getLogin() + "' ";
        cursor = db.query(TBL_NAME, new String[] { COL_ID, COL_FROM, COL_TO, COL_CONTENT},
                where,
                null, null, null, null);
        return cursor;
    }
}
