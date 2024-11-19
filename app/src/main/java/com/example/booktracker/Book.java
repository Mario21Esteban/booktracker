package com.example.booktracker;

public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int totalPages;
    private int pagesRead;

    public Book(int id, String title, String author, String genre, int totalPages, int pagesRead) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.totalPages = totalPages;
        this.pagesRead = pagesRead;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPagesRead() {
        return pagesRead;
    }

    public void setPagesRead(int pagesRead) {
        this.pagesRead = pagesRead;
    }
}
