package com.example.booktracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    private EditText titleEditText, authorEditText, pagesEditText;
    private Spinner genreSpinner;
    private Button saveBookButton;
    private RecyclerView booksRecyclerView;
    private BookAdapter bookAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Inicializa RecyclerView
        booksRecyclerView = findViewById(R.id.booksRecyclerView);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (booksRecyclerView == null) {
            Log.e("AddBookActivity", "no encontrado en el layout.");
            return;
        }

        // Configura el Spinner (ya configurado en tu código)
        Spinner genreSpinner = findViewById(R.id.genreSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.book_genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        // Configurar acción del botón para guardar el libro
        Button saveBookButton = findViewById(R.id.saveBookButton);
        saveBookButton.setOnClickListener(v -> saveBook());

        // Cargar libros en el RecyclerView
        loadBooks();
    }

    private void loadBooks() {
        // Obtener todos los libros desde la base de datos
        List<Book> books = DatabaseHelper.getInstance(this).getAllBooks();
        if (bookAdapter == null) {
            bookAdapter = new BookAdapter(books); // Inicializa el adaptador
            booksRecyclerView.setAdapter(bookAdapter); // Configura el RecyclerView
        } else {
            bookAdapter.updateBooks(books); // Actualiza la lista si ya existe
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadBooks(); // Recargar la lista de libros
    }

    private void saveBook() {
        String title = titleEditText.getText().toString().trim();
        String author = authorEditText.getText().toString().trim();
        String genre = genreSpinner.getSelectedItem().toString();
        String pages = pagesEditText.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || genre.isEmpty() || pages.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalPages;
        try {
            totalPages = Integer.parseInt(pages);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Número de páginas inválido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Agregar libro a la base de datos
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        db.addBook(title, author, genre, totalPages);

        Toast.makeText(this, "Libro guardado exitosamente.", Toast.LENGTH_SHORT).show();
        finish(); // Regresar a la pantalla principal
    }



}
