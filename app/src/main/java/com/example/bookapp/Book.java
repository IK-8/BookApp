package com.example.bookapp;

import java.text.DecimalFormat;

public class Book {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private  int imgRes;
    private  String title,author,description;
    private  double price;

    public Book(int imgRes, String title, String author, String description, double price) {
        this.imgRes = imgRes;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
    }
    public Book(int id, int imgRes, String title, String author, String description, double price) {
        this.id = id;
        this.imgRes = imgRes;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
    }



    public int getImgRes() {
        return imgRes;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
    public String getStringPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00 р");
        return decimalFormat.format(price);
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
