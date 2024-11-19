package com.example.booktracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BookTracker.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper instance;

    // Tablas y columnas
    private static final String TABLE_BOOKS = "books";
    private static final String COLUMN_BOOK_ID = "book_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_TOTAL_PAGES = "total_pages";
    private static final String COLUMN_PAGES_READ = "pages_read";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BOOKS = "CREATE TABLE " + TABLE_BOOKS + " ("
                + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_AUTHOR + " TEXT, "
                + COLUMN_GENRE + " TEXT, "
                + COLUMN_TOTAL_PAGES + " INTEGER, "
                + COLUMN_PAGES_READ + " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_TABLE_BOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    // Insertar un nuevo libro
    public void addBook(String title, String author, String genre, int totalPages) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_TOTAL_PAGES, totalPages);

        db.insert(TABLE_BOOKS, null, values);
        db.close();
    }

    // Obtener todos los libros
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BOOKS, null, null, null, null, null, COLUMN_TITLE + " ASC");
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PAGES)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PAGES_READ))
                );
                books.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return books;
    }

    // Actualizar el progreso de lectura
    public void updateProgress(int bookId, int pagesRead) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAGES_READ, pagesRead);

        db.update(TABLE_BOOKS, values, COLUMN_BOOK_ID + "=?", new String[]{String.valueOf(bookId)});
        db.close();
    }
}
