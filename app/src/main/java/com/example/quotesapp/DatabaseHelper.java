package com.example.quotesapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.example.quotesapp.Model.Quotes;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "mydb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE Quotes (_id INTEGER PRIMARY KEY AUTOINCREMENT, quote TEXT, author TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Quotes");
        onCreate(sqLiteDatabase);
    }

    public boolean insertQuotes(String quote, String author) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quote", quote);
        contentValues.put("author", author);

        long result = sqLiteDatabase.insert("Quotes", null, contentValues);

        return result != -1;
    }

    public ArrayList<Quotes> getAllData() {
        ArrayList<Quotes> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Quotes", null);

        while(cursor.moveToNext()) {
            String quote = cursor.getString(1);
            String author = cursor.getString(2);

            Quotes quotes = new Quotes(quote, author);

            arrayList.add(quotes);
        }

        return arrayList;
    }

    public String[] getAuthors() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT author FROM Quotes", null);
        String[] authors = new String[cursor.getCount() + 1];
        authors[0] = "-----";
        int i = 1;
        while(cursor.moveToNext()) {
            String author = cursor.getString(0);
            Quotes quotes = new Quotes(author);
            authors[i] = quotes.getAuthor();
            i++;
        }

        return authors;
    }

    public ArrayList<Quotes> selectedAuthor(String author) {
        ArrayList<Quotes> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Quotes WHERE author = ?", new String[] {author});

        while(cursor.moveToNext()) {
            String quote = cursor.getString(1);
            String dupAuthor = cursor.getString(2);

            Quotes quotes = new Quotes(quote, dupAuthor);

            arrayList.add(quotes);
        }

        return arrayList;
    }
}
