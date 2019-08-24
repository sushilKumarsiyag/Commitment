package com.aunico.os.commitment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CompletedTaskSqlite extends SQLiteOpenHelper {

    public static final String Databasename="Commitmentcomptask_db";
    public static final String TableName1="Completetask_tb";

    public static final String COL_1="ID";
    public static final String COL_2="Task";
    public static final String COL_3="date";
    public static final String COL_4="time";
    public static final String COL_5="Priority";
    public CompletedTaskSqlite(Context context)
    {
        super(context,Databasename,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TableName1+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,Task TEXT,date TEXT,time TEXT,Priority TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TableName1);


    }

    public boolean insertdatacompletetask(String task,String date,String time,String prority)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,task);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,time);
        contentValues.put(COL_5,prority);
        long result=db.insert(TableName1,null,contentValues);
        db.close();
        if (result==-1)
            return false;
        else
            return true;
    }


    public Cursor getcompletetaskdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from "+TableName1,null);
        return res;

    }

    public void deleteAllcomptaskdata()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        //db.execSQL("delete * from"+ TABLE_NAME);
        db.delete(TableName1,null,null);
        db.close();
    }


    public Integer deletedata(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int i=db.delete(TableName1,"Task=?",new String[]{id});
        return i;
    }

}
