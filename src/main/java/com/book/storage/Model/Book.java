package com.book.storage.Model;

import org.springframework.stereotype.Component;

@Component
public class Book {

    private long barcode;
    private String name;
    private String author;
    private int quantity;
    private double unitPrice;
    private int year=0;
    private int scIndex=0;

    public Book() {
    }

    public Book(long barcode, String name, String author, int quantity, double unitPrice) {
        this.barcode = barcode;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getScIndex() {
        return scIndex;
    }

    public void setScIndex(int scIndex) {
        this.scIndex = scIndex;
    }
}
