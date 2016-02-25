package org.pragyan.pragyantshirtapp;

/**
 * Created by lakshmanaram on 22/2/16.
 */
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController  extends SQLiteOpenHelper {

    public DBController(Context applicationcontext) {
        super(applicationcontext, "user.db", null, 1);
    }
    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;

        query = "CREATE TABLE notifs ( notifText TEXT,time TEXT,title TEXT)";
        database.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;

        query = "DROP TABLE IF EXISTS notifs";
        database.execSQL(query);
        onCreate(database);
    }


    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */


    public void insertNotif(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("notifText", queryValues.get("notifText"));
        values.put("time", queryValues.get("time"));
        values.put("title", queryValues.get("title"));

        //values.put("index", queryValues.get("index"));
        database.insert("notifs", null, values);
        database.close();
    }

    public void deleteNotif(String notiftxt) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //database.insert("notifs", null, values);
        database.delete("notifs", "notifText"+"= '"+notiftxt+"'",null);
        database.close();
    }


    public ArrayList<HashMap<String, String>> getAllNotifs() {
        ArrayList<HashMap<String, String>> notifsList;
        notifsList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM notifs";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("notifText", cursor.getString(0));
                map.put("time", cursor.getString(1));
                map.put("title", cursor.getString(2));

                notifsList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return notifsList;
    }

}

