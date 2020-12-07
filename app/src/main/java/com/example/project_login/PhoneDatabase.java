package com.example.project_login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.zip.CheckedOutputStream;
/*
 *Below is the class for the Database for phones.
 * Database Name:phones
 * Table Name for the phones :phones_info
 * Columns are the Make(eg:Samsung),Model(eg:Galaxy S20 Ultra),price,url for buying, image url for the picasso(Picassso library is used to cache the image and download),Rating,ROM,RAM,Battery Info and The Processor
 * The onUpgrade method is called when the version of the database is changed from 1.
 * addPhoneInfo method is used to add the Phones To The DATABASE ONCE !!
 * selectAllPhones(double price) will retrieve all the phones from the database whose price is less than that given by the progress bar
 * selectAllPhones() retrieves all the phones from the Database
 * selectAllPhonesByNamePrice(String make,double price) retrives all the phones from the database whose name and price are specified
 * NOTE:THE PHONES FETCHED FROM THE DATABASE ARE ADDED TO THE ARRAYLIST, FROM WHERE IT IS PASSED ON TO THE RECYCLER VIEW.
 */
public class PhoneDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME="phones.db";
    private static final int version=1;
    private static final String TABLE="phone_info";
    private static final String PHONE_MAKE="make";
    private static final String PHONE_NAME="name";
    private static final String PHONE_PRICE="price";
    private static final String BUY_URL="buy";
    private static final String PHONE_IMAGE="image";
    private static final String RATING="rating";
    private static final String RAM="ram";
    private static final String ROM="rom";
    private static final String BATTERY="battery";
    private static final String PROCESSOR="processor";
    public PhoneDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+TABLE+" ("+
                PHONE_MAKE+" TEXT, "+
                PHONE_NAME+" TEXT, "+
                PHONE_IMAGE+" TEXT, "+
                PHONE_PRICE+" DOUBLE, "+
                BUY_URL+" TEXT, "+
                RATING+" TEXT, "+
                RAM+" TEXT, "+
                ROM+" TEXT, "+
                BATTERY+" TEXT, "+
                PROCESSOR+" TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
    //Ading Phones to the Database
    public void addPhoneInfo(String make,String name,String image,Double price,String buy_url_link,String rating,String ram,String rom,String battery,String processor){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PHONE_MAKE,make);
        contentValues.put(PHONE_NAME,name);
        contentValues.put(PHONE_IMAGE,image);
        contentValues.put(PHONE_PRICE,price);
        contentValues.put(BUY_URL,buy_url_link);
        contentValues.put(RATING,rating);
        contentValues.put(RAM,ram);
        contentValues.put(ROM,rom);
        contentValues.put(BATTERY,battery);
        contentValues.put(PROCESSOR,processor);
        long result=sqLiteDatabase.insert(TABLE,null,contentValues);
        if(result!=-1){
            Log.d("Success DB","Adding Phones To Db Success !");
        }
        else {
            Log.d("Failed DB","Adding Phones To Db Failed !");
        }
    }
    //All Phones with given price segments are selected !
    Cursor selectAllPhones(double price){
        String sql="SELECT * FROM "+TABLE+" WHERE "+PHONE_PRICE+" <"+price+" ORDER BY "+PHONE_PRICE+" ASC";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;
        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        return  cursor;
    }
    Cursor selectAllPhones(double price,String rom){
        String sql="SELECT * FROM "+TABLE+" WHERE "+ROM+"='"+rom+"' AND "+PHONE_PRICE+" <"+price+" ORDER BY "+PHONE_PRICE+" ASC";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;
        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        return  cursor;
    }
    //All Phones of all price segments are selected
    Cursor selectAllPhones(){
        String sql="SELECT * FROM "+TABLE;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;
        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        return  cursor;
    }
    //This method selects all the phones from the database whose name and price are specified
    Cursor selectAllPhonesByNamePrice(String make,double price){
        //The Sql query for phone retrieval
        String sql="SELECT * FROM "+TABLE+" WHERE "+PHONE_MAKE+" ='"+make+"' AND "+PHONE_PRICE+" <"+price+";";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;

        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        //In the checking function, the cursor is checked whether it has a next row !
        return  cursor;
    }
    //Selecting The Phones Based on The Price and Rom Informations(Show All)
    Cursor selectAllPhonesByNamePriceAndROM(String make,double price,String rom){
        //The Sql query for phone retrieval
        String sql="SELECT * FROM "+TABLE+" WHERE "+PHONE_MAKE+" ='"+make+"' AND "+ROM+"='"+rom+"' AND "+PHONE_PRICE+" <"+price+";";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;

        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        //In the checking function, the cursor is checked whether it has a next row !
        return  cursor;
    }
    //Select Statement For Single RAM Selection
    Cursor selectAllPhonesByNamePriceAndRAM(String make,double price,String ram){
        //The Sql query for phone retrieval
        String sql="SELECT * FROM "+TABLE+" WHERE "+PHONE_MAKE+" ='"+make+"' AND "+RAM+"='"+ram+"' AND "+PHONE_PRICE+" <"+price+";";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;

        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        //In the checking function, the cursor is checked whether it has a next row !
        return  cursor;
    }
    //Select Phones By Price,RAM and ROM
    Cursor selectAllPhonesByNamePriceROMAndRAM(String make,double price,String rom,String ram){
        //The Sql query for phone retrieval
        String sql="SELECT * FROM "+TABLE+" WHERE "+PHONE_MAKE+" ='"+make+"' AND "+ROM+" ='"+rom+"' AND "+RAM+"='"+ram+"' AND "+PHONE_PRICE+" <"+price+";";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;

        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        //In the checking function, the cursor is checked whether it has a next row !
        return  cursor;
    }
    //Select Only for Show All
    Cursor selectAllPhonesByPriceAndRAM(double price,String ram){
        //The Sql query for phone retrieval
        String sql="SELECT * FROM "+TABLE+" WHERE "+RAM+"='"+ram+"' AND "+PHONE_PRICE+" <"+price+";";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;

        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        //In the checking function, the cursor is checked whether it has a next row !
        return  cursor;
    }

    public Cursor selectAllPhonesPriceRamRom(double price, String rom, String ram) {
        //The Sql query for phone retrieval
        String sql="SELECT * FROM "+TABLE+" WHERE "+ROM+" = '"+rom+"' AND "+RAM+" ='"+ram+"' AND "+PHONE_PRICE+" <"+price+";";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=null;

        if(sqLiteDatabase!=null){
            cursor=sqLiteDatabase.rawQuery(sql,null);
        }
        //In the checking function, the cursor is checked whether it has a next row !
        return  cursor;
    }
}
