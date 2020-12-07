package com.example.project_login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseAssist extends SQLiteOpenHelper {
    Context context;
    private  static  final String DATABASE_NAME="Info.db";
    private  static  final int VERSION=1;
    private  static final  String TABLE="Users";
    private static final String USERNAME="username";
    private static final String EMAIL="email";
    private static final String PASSWORD="password";
    public DataBaseAssist(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create a Table Users To Store The User Information !
        String sql_create_table="CREATE TABLE "+TABLE+" ("+USERNAME+" TEXT PRIMARY KEY, "+EMAIL+" TEXT, "+PASSWORD+" TEXT);";
        db.execSQL(sql_create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
    //Below is The Function to Add Users to The Database !
    Boolean addUser(String username,String email,String password){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USERNAME,username);
        contentValues.put(EMAIL,email);
        contentValues.put(PASSWORD,password);
        //Actually Inserting into the Database
        long result= sqLiteDatabase.insert(TABLE,null,contentValues);
        if(result!=-1){
            //Return True If the User is Added and False if he is not Added !
            return true;
        }
        return false;

    }
    //Used to Check Whether Alredy Exists !
    Cursor checkCurrentUser(String username){
        String sql="SELECT * FROM "+TABLE+" where username ='"+username+"'";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;
        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        return  cursor;
    }
    //Authenticate The User,if exists in the Database !
    Cursor authUser(String username,String password){
        String sql="SELECT * FROM "+TABLE+" where username ='"+username+"' and password = '"+password+"'";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;
        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        return  cursor;
    }
    //get All User Info
    String []info(String username){
        String sql="SELECT * FROM "+TABLE+" where username ='"+username+"'";
        String []arr=new String[3];
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;
        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
            if(cursor.moveToNext()){
                arr[0]=cursor.getString(0);
                arr[1]=cursor.getString(1);
                arr[2]=cursor.getString(2);
            }
        }
        return arr;
    }
    public Boolean deleteUser(String username){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        long result=sqLiteDatabase.delete(TABLE,"username=?",new String[]{username});
        if(result!=-1){
            return true;
        }
        return false;
    }
    public Boolean upDateUser(String username,String email,String pass){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(EMAIL,email);
        contentValues.put(PASSWORD,pass);
        long result=sqLiteDatabase.update(TABLE,contentValues,"username=?",new String[]{username});
        if(result!=-1){
            return true;
        }
        return false;
    }
}
