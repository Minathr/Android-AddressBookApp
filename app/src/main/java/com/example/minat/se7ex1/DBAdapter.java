package com.example.minat.se7ex1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	
	//various constants to be used in creating and updating the database
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_PHONE = "phone";
    static final String KEY_EMAIL = "email";
    static final String KEY_ADDRESS = "address";
    static final String KEY_CITY = "city";
    static final String KEY_STATECODE = "stateCode";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyContactsDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
        "create table contacts (_id integer primary key autoincrement, "
        + "name text not null, phone text not null, "
        + "email text, address text, city text, stateCode text );";

    final Context context;

    
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //onCreate implicitly used to create the database table "contacts"
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //onUpgrade called implicitly when the database "version" has changed
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    //calls SQLiteOpenHelper.getWritableDatabase() when opening the DB.
    //If the DB does not yet exist it will be created here.
    public DBAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() 
    {
        dbHelper.close();
    }

    //---insert a contact into the database---
    //uses ContentValues class to store key/value pairs for field names and data 
    //to be inserted into the DB table by SQLiteDatabase.insert()
    public long insertContact(String name, String phone, String email, String address, String city, String stateCode )
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_PHONE, phone);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_ADDRESS, address);
        initialValues.put(KEY_CITY, city);
        initialValues.put(KEY_STATECODE, stateCode);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    //removes from the DB the record specified using SQLiteDatabase.delete()
    public boolean deleteContact(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    //SQLiteDatabase.query builds a SELECT query and returns a "Cursor" over the result set 
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_PHONE, KEY_EMAIL, KEY_ADDRESS, KEY_CITY, KEY_STATECODE}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                                KEY_PHONE, KEY_EMAIL, KEY_ADDRESS, KEY_CITY, KEY_STATECODE},
                                KEY_ROWID + "=" + rowId, null,
                                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    //Uses SQLiteDatabase.update() to change existing data by key/value pairs
    public boolean updateContact(long rowId, String name, String phone, String email, String address, String city, String stateCode)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_PHONE, phone);
        args.put(KEY_EMAIL, email);
        args.put(KEY_ADDRESS, address);
        args.put(KEY_CITY, city);
        args.put(KEY_STATECODE, stateCode);
        boolean success = false;
        try{
            success = (db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0);
        }
        catch (Exception e){
            String error =  e.getMessage().toString();
        }
        return success;
    }

}
