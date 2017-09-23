//package com.android.bigserj.data.database;
//
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.android.bigserj.data.dbEntity.Country;
//import com.android.bigserj.data.dbEntity.User;
//
//import java.util.List;
//
//public class DatabaseManager {
//
//    private Context context;
//    private DBHelper dbHelper;
//    private SQLiteDatabase sqLiteDatabase;
//
//    public DatabaseManager(Context context) {
//        this.context = context;
//        dbHelper = new DBHelper(context);
//    }
//
//    // открываем бд
//    public void open(boolean isWritable){
//        if (isWritable)
//            sqLiteDatabase = dbHelper.getWritableDatabase();
//        else
//            sqLiteDatabase = dbHelper.getReadableDatabase();
//    }
//
//    // закртываем бд
//    public void close(){
//        if (sqLiteDatabase!=null)
//            sqLiteDatabase.close();
//    }
//
//    public void insertUser(User user){
//
//        StringBuilder sql = new StringBuilder();
//        sql.append("INSERT INTO user ( 'name', 'age', 'countryId') ");
//        sql.append("VALUES (");
////        sql.append(user.getId());
//        sql.append("'");
//        sql.append(user.getName());
//        sql.append("',");
//        sql.append(user.getAge());
//        sql.append(",");
//        sql.append(user.getCountry().getId());
//        sql.append(")");
//        sqLiteDatabase.execSQL(sql.toString());
//        Log.e("DatabaseManager", "insertUser() sql = "+sql.toString());
//
//        sql.delete(0,sql.length());
//        sql.append("INSERT INTO country ('name') ");
//        sql.append("VALUES ('");
//        sql.append(user.getCountry().getName());
//        sql.append("')");
//
//        // INSERT INTO ('name', 'age', 'countryId') VALUES ('Name', 25, 0)
//
//        Log.e("DatabaseManager", "insertUser() sql = "+sql.toString());
//        sqLiteDatabase.execSQL(sql.toString());
//
//
//    }
//
//    public void updateUser(User user){
//
//    }
//
//    public List<User> getUsers(){
//
//        return null;
//    }
//
//    public User getUserById(String name){
//
////        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user INNER JOIN country ON " +
////                "user.countryId = country.id WHERE user.id = ?", new String[]{String.valueOf(name)});
//
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user INNER JOIN country " +
//                "WHERE user.name = ?", new String[]{String.valueOf(name)});
//
//        if (cursor!=null) {
//            if (cursor.moveToFirst()) {
//                User user = new User();
//
//                // вытягиваем данные из Cursor
//
//                int userId = cursor.getInt(0);
//                String name2 = cursor.getString(1);
//                int age = cursor.getInt(2);
//                int countryId = cursor.getInt(4);
//                String countryName = cursor.getString(5);
//
//                // заполняем объет user
//                user.setId(userId);
//                user.setName(name2);
//                user.setAge(age);
//
//                // заполняем объет country
//                Country country = new Country();
//                country.setId(countryId);
//                country.setName(countryName);
//
//                // добавляем объект Country в User
//                user.setCountry(country);
//
//                cursor.close();
//
//                return user;
//
//            }else
//                Log.e("moveToFirst()", " !moveToFirst"+"  "+cursor.getCount());
//        }else
//            Log.e("DatabaseManager", "getUserById() cursor is null");
//
//
//        return null;
//    }
//
//
//}
