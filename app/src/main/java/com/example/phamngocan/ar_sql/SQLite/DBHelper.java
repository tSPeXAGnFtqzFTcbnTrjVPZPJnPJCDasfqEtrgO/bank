package com.example.phamngocan.ar_sql.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "NGANHANG";
    private static String TABLE_NAME = "KHACHHANG";
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void insertData(String cmnd,String ho,String ten,String diachi,
                           String phai,String ngaycap,String sodt,String macn){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EnumTableNV.CMND.toString(),cmnd);
        contentValues.put(EnumTableNV.HO.toString(),ho);
        contentValues.put(EnumTableNV.TEN.toString(),ten);
        contentValues.put(EnumTableNV.DIACHI.toString(),diachi);
        contentValues.put(EnumTableNV.PHAI.toString(),phai);
        contentValues.put(EnumTableNV.NGAYCAP.toString(),ngaycap);
        contentValues.put(EnumTableNV.MACN.toString(),macn);

        database.insert(TABLE_NAME,null,contentValues);
    }
    Cursor getAll(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " +TABLE_NAME,null);
        return cursor;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(CMND nchar(10) PRIMARY KEY," +
                " HO nvarchar(20)," +
                " TEN nvarchar(50), " +
                "DIACHI nvarchar(50)," +
                " PHAI nvarchar(50)," +
                " NGAYCAP nvarchar(50)," +
                " SODT nvarchar(50)," +
                " MACN nvarchar(50))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
