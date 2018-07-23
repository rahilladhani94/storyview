package com.storyview.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.storyview.Model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 01-06-2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TEXTSTYLE= "textstyle";
    private static final String KEY_TEXTSIZE= "textsize";
    private static final String KEY_BGCOLOR= "bgcolor";
    private static final String KEY_IMAGEPATH= "imagepath";
    private static final String KEY_DATE= "date";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_STATUS + " TEXT,"
                + KEY_TEXTSTYLE + " TEXT,"
                + KEY_TEXTSIZE + " TEXT,"
                + KEY_BGCOLOR + " TEXT,"
                + KEY_IMAGEPATH + " TEXT,"
                + KEY_DATE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }



    public void  addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, contact.getStatus()); // Contact Name
        values.put(KEY_TEXTSTYLE, contact.getFontstyle()); // Contact Phone
        values.put(KEY_TEXTSIZE, contact.getTextsize());
        values.put(KEY_BGCOLOR, contact.getBgcolor());
        values.put(KEY_IMAGEPATH, contact.getImagepath());
        values.put(KEY_DATE, contact.getDate());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // todo Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_STATUS,KEY_TEXTSTYLE,KEY_TEXTSIZE,KEY_BGCOLOR ,KEY_IMAGEPATH,KEY_DATE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
        // return contact
        return contact;
    }

    // todo Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setStatus(cursor.getString(1));
                contact.setFontstyle(cursor.getString(2));
                contact.setTextsize(cursor.getString(3));
                contact.setBgcolor(cursor.getString(4));
                contact.setImagepath(cursor.getString(5));
                contact.setDate(cursor.getString(6));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    //todo Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, contact.getStatus());
        values.put(KEY_TEXTSTYLE, contact.getFontstyle());
        values.put(KEY_TEXTSIZE, contact.getTextsize());
        values.put(KEY_BGCOLOR, contact.getBgcolor());
        values.put(KEY_IMAGEPATH, contact.getImagepath());
        values.put(KEY_DATE, contact.getDate());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // todo Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    //todo  Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM contacts");

    }
}