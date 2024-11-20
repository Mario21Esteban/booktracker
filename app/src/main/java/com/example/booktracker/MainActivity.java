package com.example.booktracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView booksRecyclerView;
    private BookAdapter bookAdapter;
    private FloatingActionButton addBookFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booksRecyclerView = findViewById(R.id.booksRecyclerView);
        addBookFab = findViewById(R.id.addBookFab);

        // Configurar RecyclerView
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadBooks();

        // Configurar el FloatingActionButton
        addBookFab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        });
    }

    private void loadBooks() {
        List<Book> books = DatabaseHelper.getInstance(this).getAllBooks();
        bookAdapter = new BookAdapter(books);
        booksRecyclerView.setAdapter(bookAdapter);
    }
}
