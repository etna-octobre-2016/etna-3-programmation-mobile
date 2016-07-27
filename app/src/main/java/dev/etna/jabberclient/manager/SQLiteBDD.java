package dev.etna.jabberclient.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Cedric Olivier on 27/07/2016.
 */
public class SQLiteBDD extends SQLiteOpenHelper {
    public static final String DB_NAME      = "pmob.db";
    public static final String TBL_NAME     = "MESSAGE_HISTORY";
    private final String COL_ID             = "ID";
    private final String COL_FROM           = "FROM_USER";
    private final String COL_TO             = "TO_USER";
    private final String COL_CONTENT        = "MESSAGE_CONTENT";

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
        db.execSQL("DROP TABLE " + TBL_NAME + ";");
        onCreate(db);
    }
}
