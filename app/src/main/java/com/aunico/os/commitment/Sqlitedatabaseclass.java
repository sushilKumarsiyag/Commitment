package com.aunico.os.commitment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqlitedatabaseclass extends SQLiteOpenHelper {

    public static final String Databasename="Commitment_db";
    public static final String TableName="Commitment_tb";

    public static final String COL_1="ID";
    public static final String COL_2="Task";
    public static final String COL_3="date";
    public static final String COL_4="time";
    public static final String COL_5="Priority";

   public Sqlitedatabaseclass(Context context)
   {
       super(context,Databasename,null,1);

   }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       sqLiteDatabase.execSQL("CREATE TABLE "+TableName+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,Task TEXT,date TEXT,time TEXT,Priority TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

       sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TableName);
    }
    public boolean insertdata(String task,String date,String time,String prority)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,task);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,time);
        contentValues.put(COL_5,prority);
        long result=db.insert(TableName,null,contentValues);
        db.close();
        if (result==-1)
            return false;
        else
            return true;
    }

    public Cursor getalldata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from "+TableName,null);
        return res;

    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.delete(TABLE_NAME,null,null);
        //db.execSQL("delete * from"+ TABLE_NAME);
        db.delete(TableName,null,null);
        db.close();
    }

    public Integer deletedata(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int i=db.delete(TableName,"Task=?",new String[]{id});
        return i;
    }

}
