package dev.etna.jabberclient.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dev.etna.jabberclient.interfaces.DataBaseConstants;


/**
 * Created by Cedric Olivier on 27/07/2016.
 */
public class SQLiteBDD extends SQLiteOpenHelper implements DataBaseConstants {


    public SQLiteBDD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TBL_NAME +" ( "
                + COL_ID +      " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_FROM +    " VARCHAR, "
                + COL_TO +      " VARCHAR, "
                + COL_CONTENT + " VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + DataBaseConstants.TBL_NAME + ";");
        onCreate(db);
    }
}
