package com.example.myapp1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//数据库访问的类，用于创建表更新表之类的
public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "myrate.db";
    public static final String TB_NAME = "tb_rates";

    //构造函数
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);

    }
    //构造函数
    public DBHelper(Context context){
        super(context, DB_NAME, null, VERSION);

    }

    //创建数据库的操作
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表
        sqLiteDatabase.execSQL("CREATE TABLE " + TB_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, CURNAME TEXT, CURRATE TEXT)");
    }

    //升级数据库的操作
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
