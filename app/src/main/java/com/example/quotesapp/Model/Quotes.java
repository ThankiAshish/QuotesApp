package com.example.quotesapp.Model;

public class Quotes {
    private int id;
    private String quote;
    private String author;

    public Quotes(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public Quotes(String author) {
        this.author = author;
    }

    public Quotes() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
