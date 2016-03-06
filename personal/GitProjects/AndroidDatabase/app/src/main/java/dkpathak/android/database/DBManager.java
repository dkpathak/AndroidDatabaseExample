package dkpathak.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dharmendra on 6/3/16.
 */
public class DBManager {
    private static DBManager dbManager;
    private static DBOpenHelper dbOpenHelper;
    private static String DB_NAME = "dkpathak.db";
    private static int DB_VERSION = 1;
    private String TAG = getClass().getName();

    private DBManager(Context context) {
        if (dbOpenHelper == null)
            dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        dbOpenHelper.getWritableDatabase();

    }

    public static DBManager getInstance(Context context) {
        if (dbManager == null)
            dbManager = new DBManager(context);
        return dbManager;
    }

    public long insertDataToDB(String tableName, ContentValues cv) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long result = db.insert(tableName, null, cv);
        db.close();
        return result;
    }

    private class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

            super(context, name, factory, version);
        }

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                            DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "OnCreate Called");
            db.execSQL(createUserTable());
        }

        private String createUserTable() {
            StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
            stringBuilder.append(TableConstants.TABLE_USER);
            stringBuilder.append("(");
            stringBuilder.append(TableConstants.COLUMN_USER_MOBILE);
            stringBuilder.append(" VARCHAR PRIMARY KEY,");
            stringBuilder.append(TableConstants.COLUMN_USER_NAME);
            stringBuilder.append(" VARCHAR, ");
            stringBuilder.append(TableConstants.COLUMN_USER_EMAIL);
            stringBuilder.append(" VARCHAR");
            stringBuilder.append(");");
            Log.i(TAG, "USERTable:" + stringBuilder.toString());
            return stringBuilder.toString();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "onUpgrade Called" + newVersion);
        }
    }
}
