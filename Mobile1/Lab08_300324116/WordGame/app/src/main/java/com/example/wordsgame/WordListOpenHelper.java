package com.example.wordsgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class WordListOpenHelper extends SQLiteOpenHelper
{
    private static final String TAG = WordListOpenHelper.class.getSimpleName();

    // has to be 1 first time or app will crash
    private static final int DATABASE_VERSION = 1;
    private static final String WORD_LIST_TABLE = "word_entries";
    private static final String DATABASE_NAME = "wordlist";

    // Column names...
    public static final String KEY_ID = "_id";
    public static final String KEY_WORD = "word";

    // ... and a string array of columns.
    private static final String[] COLUMNS = {KEY_ID, KEY_WORD};

    private static final String WORD_LIST_TABLE_CREATE =
            "CREATE TABLE " + WORD_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    // id will auto-increment if no value passed
                    KEY_WORD + " TEXT );";

    private SQLiteDatabase mWriteableDB;
    private SQLiteDatabase mReadableDB;

    public WordListOpenHelper(@Nullable Context context)
    {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(WORD_LIST_TABLE_CREATE);
        fillDatabaseWithData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(WordListOpenHelper.class.getName(),
                "Upgrading database from version " + i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE);
        onCreate(sqLiteDatabase);
    }

    private void fillDatabaseWithData(SQLiteDatabase db)
    {
        String[] words ={"Android", "Adapter", "ListView", "AsyncTask",
                "Android Studio", "SQLiteDatabase", "SQLOpenHelper",
                "Data model", "ViewHolder", "Android Performance",
                "OnClickListener"};
        ContentValues values = new ContentValues();
        for (int i = 0; i < words.length; i++)
        {
            values.put(KEY_WORD,words[i]);
            db.insert(WORD_LIST_TABLE, null, values);
        }
    }

    public long count(){
        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }
        return DatabaseUtils.queryNumEntries(mReadableDB, WORD_LIST_TABLE);
    }

    public long insert(Word word){
        long newId = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word.getmWord());
        try {
            if (mWriteableDB == null) {
                mWriteableDB = getWritableDatabase();
            }
            newId = mWriteableDB.insert(WORD_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    public int delete(int id) {
        int deleted = 0;
        try {
            if (mWriteableDB == null) {
                mWriteableDB = getWritableDatabase();
            }
            deleted = mWriteableDB.delete(WORD_LIST_TABLE, //table name
                    KEY_ID + " =? ", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());
        }
        return deleted;
    }

    public Word query(int position) {
        String query = "SELECT  * FROM " + WORD_LIST_TABLE +
                " ORDER BY " + KEY_WORD + " ASC " +
                "LIMIT " + position + ",1";
        Cursor cursor = null;

        Word entry = new Word();

        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();

            entry.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setmWord(cursor.getString(cursor.getColumnIndex(KEY_WORD)));

        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return entry;
        }
    }
}
