package com.example.sqlitetutorial;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG="DatabaseHelper";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="ST";

    //Table Names
    private static final String TB_STUDENT = "Student";

    //USER TABLE COLUMN NAMES
    private static final String tb_ID = "ID";
    private static final String tb_SName = "sName";
    private static final String tb_SMajor = "sMajor";
    private static final String tb_SID = "sID";


    //Table Create Statement
    private static final String CREATE_STUDENT_TB = "CREATE TABLE "
            + TB_STUDENT + " ( ID " + "INTEGER PRIMARY KEY, "
            + tb_SName + " TEXT, "
            + tb_SMajor + " TEXT, "
            + tb_SID +  " TEXT )";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_STUDENT_TB);
        Log.e("CREATE_STUDENT_TB",CREATE_STUDENT_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TB_STUDENT);
        onCreate(db);
    }

    public long insertStudent (String sName,String sMajor,String sID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tb_SName,sName);
        values.put(tb_SMajor,sMajor);
        values.put(tb_SID,sID);

        long id = db.insert(TB_STUDENT, null,values);

        return id;
    }

    public List<Student> getAllStudent()
    {
        List<Student> student = null;
        String selectQuery = "SELECT * FROM  " + TB_STUDENT;
        Log.e("Student Found", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst())
        {
            do{
                Student s = new Student();
                s.setsName(c.getString(c.getColumnIndex(tb_SName)));
                s.setsMajor(c.getString(c.getColumnIndex(tb_SMajor)));
                s.setsID(c.getString(c.getColumnIndex(tb_SID)));
                student.add(s);
            }while(c.moveToNext());
        }
        return student;
    }

}
