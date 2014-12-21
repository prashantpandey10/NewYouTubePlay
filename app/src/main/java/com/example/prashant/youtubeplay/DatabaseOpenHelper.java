package com.example.prashant.youtubeplay;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static DatabaseOpenHelper helper = null;
    private Context mCxt;
    public static DatabaseOpenHelper getInstance(Context ctx) {

        if (helper == null) {
            helper = new DatabaseOpenHelper  (ctx.getApplicationContext());
        }
        return helper;
    }

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mCxt = context;
    }
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "songs";

    // Table Names
    private static final String TABLE_HISTORY = "History";
    private static final String TABLE_CP = "CP";
    private static final String TABLE_SP = "SP";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_SONG_NAME = "songname";


    private static final String CREATE_TABLE_HISTORY =  "CREATE TABLE " + TABLE_HISTORY + "("
            + KEY_ID + " VARCHAR2(100) NOT NULL UNIQUE," + KEY_SONG_NAME + " VARCHAR2(100) NOT NULL UNIQUE" + ")";

    // Tag table create statement
    private static final String CREATE_TABLE_CP =  "CREATE TABLE " + TABLE_CP + "("
            + KEY_ID + " VARCHAR2(100) NOT NULL UNIQUE," + KEY_SONG_NAME + " VARCHAR2(100) NOT NULL UNIQUE" + ")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_SP = "CREATE TABLE "
            + TABLE_SP + "(" + KEY_ID + " VARCHAR2(100) NOT NULL UNIQUE,"
            + KEY_SONG_NAME + " VARCHAR2(100) NOT NULL UNIQUE" + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("HERE","TABLE CREATED");
        db.execSQL(CREATE_TABLE_HISTORY);
        db.execSQL(CREATE_TABLE_CP);
        db.execSQL(CREATE_TABLE_SP);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        // create new tables
        onCreate(db);

    }

    public void addSong(Song song,String table) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // Contact Name
        values.put(KEY_ID, song.getId());
        values.put(KEY_SONG_NAME, song.getName());
        Log.d("HERE","values "+values);

        if(table.equals("HISTORY"))
        db.insert(TABLE_HISTORY, null, values);
        else if(table.equals("CP"))
            db.insert(TABLE_CP, null, values);
        else if(table.equals("SP"))
            db.insert(TABLE_SP, null, values);

        db.close(); // Closing database connection
    }

    public ArrayList<Song> getAllSongs(String table) {
        ArrayList<Song> songsList = new ArrayList<Song>();
        // Select All Query
        String selectQuery=null;
        if(table.equals("HISTORY"))
        {selectQuery = "SELECT  * FROM " + TABLE_HISTORY;}
        else if(table.equals("CP"))
        {   selectQuery = "SELECT  * FROM " + TABLE_CP;}
        else if(table.equals("SP")) {
           selectQuery = "SELECT  * FROM " + TABLE_SP;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                String name=cursor.getString(0);
                String id=cursor.getString(1);
                Song song=new Song(name,id);
                // Adding contact to list
                songsList.add(song);
            } while (cursor.moveToNext());
        }

        // return contact list
        return songsList;
    }


    // Deleting single contact
    public void deleteSong(Song song,String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(table.equals("HISTORY")){
        db.delete(TABLE_HISTORY, KEY_ID + " = ?",
                new String[] { String.valueOf(song.getId()) });}
        if(table.equals("CP")){
            db.delete(TABLE_CP, KEY_ID + " = ?",
                    new String[] { String.valueOf(song.getId()) });}
        if(table.equals("SP")){
            db.delete(TABLE_SP, KEY_ID + " = ?",
                    new String[] { String.valueOf(song.getId()) });}
        db.close();
    }
}
