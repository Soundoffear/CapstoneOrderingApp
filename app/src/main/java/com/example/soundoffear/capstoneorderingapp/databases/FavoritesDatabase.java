package com.example.soundoffear.capstoneorderingapp.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.soundoffear.capstoneorderingapp.contracts.FavoritesContract;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fav_db";
    private static final int DATABASE_VERSION = 1;

    public FavoritesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_FAV_TABLE = "CREATE TABLE " + FavoritesContract.FavoritesEntry.FAVORITES_TABLE_NAME + "("
                + FavoritesContract.FavoritesEntry._ID + " INTEGER PRIMARY KEY, "
                + FavoritesContract.FavoritesEntry.FAVORITES_ITEM_NAME + " TEXT NOT NULL )";
        db.execSQL(CREATE_FAV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoritesContract.FavoritesEntry.FAVORITES_TABLE_NAME);
        onCreate(db);
    }

    public void insertToFavoritesDB(String favName) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoritesContract.FavoritesEntry.FAVORITES_ITEM_NAME, favName);

        database.insert(FavoritesContract.FavoritesEntry.FAVORITES_TABLE_NAME, null, contentValues);

    }

    public List<String> getAllFavValues() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<String> list = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(FavoritesContract.FavoritesEntry.FAVORITES_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()) {
            do {
                String favName = cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.FAVORITES_ITEM_NAME));
                list.add(favName);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteItem(int position) {
        SQLiteDatabase database = getWritableDatabase();
        String[] whereArgs = new String[] {String.valueOf(position)};
        database.delete(FavoritesContract.FavoritesEntry.FAVORITES_TABLE_NAME, "_id=?", whereArgs);
    }

    public void deleteAll() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(FavoritesContract.FavoritesEntry.FAVORITES_TABLE_NAME, null,null);
    }
}
