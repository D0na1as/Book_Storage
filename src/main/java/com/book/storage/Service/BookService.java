package com.book.storage.Service;

import com.book.storage.Model.Book;
import com.book.storage.Repository.BookRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    private final String database = "src/main/resources/static/database";

    @Autowired
    ObjectMapper mapper;

    private final JSONParser parser = new JSONParser();


    public void addBook (Book book) throws IOException, ParseException {
        bookRepo.addBook(book);
    }

    public String getBook (String barcode) throws ParseException, IOException {

        return bookRepo.getBook(barcode);
    }

    public void setField(String barcode, String field, String value) throws IOException, ParseException {

        JSONArray jArray = getBookJSON(barcode);
        JSONObject jObject = (JSONObject) jArray.get(0);
        jArray = getLibrary();
        jArray.remove(jObject);

        switch(field.toLowerCase().trim()) {
            case "title":
                jObject.replace("Title", value.trim());
                saveJSONBook(jObject, jArray);
                break;
            case "author":
                jObject.replace("Author", value.trim());
                saveJSONBook(jObject, jArray);
                break;
            case "quantity":
                jObject.replace("Quantity", value.trim());
                saveJSONBook(jObject, jArray);
                break;
            case "price":
                jObject.replace("Price", value.trim());
                saveJSONBook(jObject, jArray);
                break;
            case "year":
                jObject.replace("Year", value.trim());
                saveJSONBook(jObject, jArray);
                break;
            case "index":
                jObject.replace("Index", value.trim());
                saveJSONBook(jObject, jArray );
                break;

        }
    }

    public String calculatePrice(String barcode) throws IOException, ParseException {

        JSONArray jBook = getBookJSON(barcode);
        JSONObject jObject = (JSONObject) jBook.get(0);
        Book book = mapper.readValue(jObject.toJSONString(), new TypeReference<Book>() {});
        double price = 0;
        price = book.getQuantity() * book.getUnitPrice();

        if (book.getYear()!=null) {
            price = price * (getDate() - book.getYear());
            return String.format("%1$,.2f", price);
        } else if (book.getScIndex()!=null) {
            price = price * book.getScIndex();
            return String.format("%1$,.2f", price);
        }
            return String.format("%1$,.2f", price);
    }

    private void saveJSONBook(JSONObject jObject, JSONArray jArray) throws IOException, ParseException {
        bookRepo.addBookJSON(jObject, jArray);
    }

    private JSONArray getBookJSON (String barcode) throws IOException, ParseException {
         return (JSONArray) parser.parse(bookRepo.getBook(barcode));
    }

    private JSONArray getLibrary () throws IOException, ParseException {
        return (JSONArray) parser.parse(bookRepo.getDatabase());
    }

    private int getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date time = new Date();
        return Integer.parseInt(dateFormat.format(time));
    }

}
