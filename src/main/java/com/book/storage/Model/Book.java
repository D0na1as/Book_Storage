package com.book.storage.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

    @JsonProperty("Barcode")
    private String barcode;
    @JsonProperty("Title")
    private String name;
    @JsonProperty("Author")
    private String author;
    @JsonProperty("Quantity")
    private int quantity;
    @JsonProperty("Price per Unit")
    private double unitPrice;
    @JsonProperty("Release Year")
    private Integer year;
    @JsonProperty("Science Index")
    private Integer scIndex;

    public Book() {
    }

    public Book(String barcode, String name, String author, int quantity, double unitPrice) {
        this.barcode = barcode;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getScIndex() {
        return scIndex;
    }

    public void setScIndex(Integer scIndex) {
        this.scIndex = scIndex;
    }

    public JSONObject toJSONObject () {

        JSONObject json = new JSONObject();
        json.put("Barcode", barcode);
        json.put("Title", name);
        json.put("Author", author);
        json.put("Quantity", quantity);
        json.put("Price per Unit", unitPrice);
        json.put("Release Year", year);
        json.put("Science Index", scIndex);

        return json;
    }
}
