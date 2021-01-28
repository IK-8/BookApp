package com.example.bookapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import static com.example.bookapp.MainActivity.costil;


public class DataSaver {
    public  static  final  String POSITION="POSITION";
    private  static  DataSaver instance;

    public static DataSaver getInstance(Context context) {
        if(instance==null)
            instance = new DataSaver(context);
        return instance;
    }
    private final DataHelper dataHelper;

    private MutableLiveData<List<Book>> books = new MutableLiveData<>();
    public DataSaver(Context context) {
    dataHelper = new DataHelper(context);
        List<Book> booksList = new ArrayList<>();
        readBook(booksList);
        books.setValue(booksList);
    }

    public MutableLiveData<List<Book>> getBooks() {
        return books;
    }

    private final String DB_NAME = "BookDB";
private final String BOOK_TABLE_NAME = "Book";
private final String ID_COLUMN = "id";
private final String IMG_RES_COLUMN = "imgRes";
private final String TITLE_COLUMN = "title";

private final String AUTHOR_COLUMN = "author";
private final String DESCRIPTION_COLUMN = "description";
private final String PRICE_COLUMN = "price";
private final int    DB_VERSION = 1;

    public void addBook(Book book)
    {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // подключаемся к БД
        SQLiteDatabase db = dataHelper.getWritableDatabase();
            cv.put(TITLE_COLUMN, book.getTitle());
            cv.put(AUTHOR_COLUMN, book.getAuthor());
            cv.put(DESCRIPTION_COLUMN, book.getDescription());
            cv.put(IMG_RES_COLUMN, book.getImgRes());
            cv.put(PRICE_COLUMN, book.getPrice());
        book.setId((int)db.insert(BOOK_TABLE_NAME, null, cv));

        costil();
        update();
    }

    private void update() {
        books.setValue(books.getValue());
    }

    public void updateBook(Book book)
    {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // подключаемся к БД
        SQLiteDatabase db = dataHelper.getWritableDatabase();
            cv.put(TITLE_COLUMN, book.getTitle());
            cv.put(AUTHOR_COLUMN, book.getAuthor());
            cv.put(DESCRIPTION_COLUMN, book.getDescription());
            cv.put(IMG_RES_COLUMN, book.getImgRes());
            cv.put(PRICE_COLUMN, book.getPrice());
        db.update(BOOK_TABLE_NAME, cv, "id = ?",
                new String[] {String.valueOf(book.getId())});

        costil();

        update();
    }
    public void readBook(List<Book> books)
    {
        books.clear();
        // подключаемся к БД
        SQLiteDatabase db = dataHelper.getWritableDatabase();
        Cursor c = db.query(BOOK_TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {


            int idColIndex = c.getColumnIndex(ID_COLUMN);
            int imgResColIndex = c.getColumnIndex(IMG_RES_COLUMN);
            int titleColIndex = c.getColumnIndex(TITLE_COLUMN);
            int authorColIndex = c.getColumnIndex(AUTHOR_COLUMN);
            int descriptionColIndex = c.getColumnIndex(DESCRIPTION_COLUMN);
            int priceColIndex = c.getColumnIndex(PRICE_COLUMN);
            do {
                books.add(new Book(c.getInt(idColIndex),
                        c.getInt(imgResColIndex),
                        c.getString(titleColIndex),
                        c.getString(authorColIndex),
                        c.getString(descriptionColIndex),
                        c.getDouble(priceColIndex)

                        ));
            }while (c.moveToNext());
            }



    }


    private class DataHelper extends SQLiteOpenHelper{

        public DataHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+BOOK_TABLE_NAME+" ("
                    + ID_COLUMN + " integer primary key autoincrement,"
                    + IMG_RES_COLUMN        +" text,"
                    + TITLE_COLUMN          +" text,"
                    + AUTHOR_COLUMN         +" text,"
                    + DESCRIPTION_COLUMN    +" text,"
                    + PRICE_COLUMN          +" real"+ ");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
