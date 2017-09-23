//package com.android.bigserj.data.database;
//
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//public class DBHelper extends SQLiteOpenHelper{
//
//    public static final String DATABASE_NAME = "test";
//    public static final int VERSION = 1;
//
//
//    public DBHelper(Context context) {
//        super(context, DATABASE_NAME, null, VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        Log.e("DBHelper", "onCreate()");
//
//        db.execSQL("CREATE TABLE user " +
//                "('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "'name' TEXT," +
//                "'age' INTEGER," +
//                "'countryId' INTEGER)");
//
//        db.execSQL("CREATE TABLE country " +
//                "('id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "'name' TEXT)");
//
//
//
//
//
//
////        db.beginTransaction();  //  транзакция, если в одной из операция внутри произойдет ошибка
////        db.endTransaction();  // то все операции откатятся в остоянии на момент вызова beginTransaction
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        Log.e("DBHelper", "onUpgrade()");
//    }
//
//
//
//}
