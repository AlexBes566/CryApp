package com.example.cryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "CONTACTS"; //название таблицы
    public static final String LOGIN = "LOGIN"; //Имя контакта
    public static final String BLOCK_ADDRESS = "BLOCKCHAIN_ADDRESS"; //адрес контакта в блокчейн системе
    private static final int DATABASE_VERSION = 1;

    public SQLDB(Context context){
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(id integer primary key autoincrement," +
                LOGIN + "," + BLOCK_ADDRESS +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertDB (SQLiteDatabase db, String login, String block_address){
        ContentValues newValues = new ContentValues();
        newValues.put(LOGIN, login);
        newValues.put(BLOCK_ADDRESS, block_address);
        db.insert(TABLE_NAME, null, newValues);
    }

    public Cursor read_data_base(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_NAME,null, null, null, null, null, null);
        return cursor;
    }

    public void delete_row_data_base(SQLiteDatabase db, String login){
        db.delete(TABLE_NAME, "LOGIN = ?", new String[]{login});
    }
}
