package com.example.myapp1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

//数据库业务的操作类，通过这个类对数据库进行管理，并且对外面提供方法
public class RateManager {
    private DBHelper dbHelper;
    private String TBNAME;

    //构造函数
    public RateManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    //插入一条数据
    public void add(RateItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CURNAME", item.getCurName());
        values.put("CURRATE", item.getCurRate());
        db.insert(TBNAME, null, values);
        db.close();
    }

    //插入多条数据
    public void addAll(List<RateItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (RateItem i : list){
            ContentValues values = new ContentValues();
            values.put("CURNAME", i.getCurName());
            values.put("CURRATE", i.getCurRate());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    //显示所有数据操作函数
    public List<RateItem> showListAll(){
        List<RateItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor != null){
            rateList = new ArrayList<RateItem>();
            while (cursor.moveToNext()){
                RateItem item = new RateItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;
    }

    //删除一条数据
    public void delete(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //删除所有数据操作函数
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete(TBNAME, null, null);
        db.close();
    }

    //更新操作
    public void update(RateItem item){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("CURNAME", item.getCurName());
        values.put("CURRATE", item.getCurRate());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    //查询返回结果操作
    public  RateItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        RateItem item = null;
        if(cursor!=null && cursor.moveToFirst()){
            item = new RateItem();
            item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
            item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
            cursor.close();
        }
        db.close();
        return item;
    }

}
