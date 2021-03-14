package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlDatabaseHelper extends SQLiteOpenHelper {

    private static Integer Version=1;

    //构造函数
    public SqlDatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }

    public SqlDatabaseHelper(Context context,String name,int version){
        this(context,name,null,version);
    }

    public SqlDatabaseHelper(Context context,String name){
        this(context,name,Version);
    }

    //创建数据库时
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create database and table");
        String sql="create table note (id int primary key,title varchar(200),texturi varchar(200))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    System.out.println("Version has changed to "+newVersion);
    }
}
