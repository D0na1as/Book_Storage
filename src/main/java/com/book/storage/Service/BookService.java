package com.book.storage.Service;

import com.book.storage.Model.Book;
import com.book.storage.Repository.BookRepo;
import com.book.storage.Utility.Util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    Util util;

    private final String database = "src/main/resources/static/database";
    private final JSONParser parser = new JSONParser();


    public void addBook (Book book) throws IOException, ParseException {
        bookRepo.addBook(book);
    }

    //Add book to database when book provided in JSON format
    private void addJSONBook(JSONObject jObject, JSONArray jArray) throws IOException, ParseException {
        bookRepo.addBookJSON(jObject, jArray);
    }

    public Book getBook(String barcode) throws ParseException, IOException {

        JSONArray array = getBookJSON(barcode);
        if (array.size()>0) {
            JSONObject jObject = (JSONObject) array.get(0);
            Book book = mapper.readValue(jObject.toJSONString(), new TypeReference<Book>() {
            });
            return book;
        } else {
            return null;
        }
    }

    public String getBookString(String barcode) throws ParseException, IOException {

        return bookRepo.getBook(barcode);
    }


    //Get book from database in JSON format
    private JSONArray getBookJSON (String barcode) throws IOException, ParseException {

        String book = bookRepo.getBook(barcode);
        if (book!=null){
            return (JSONArray) parser.parse(book);
        } else  {
            return null;
        }
    }

    public void setField(String barcode, String field, String value) throws IOException, ParseException {

        JSONArray jArray = getBookJSON(barcode);
        JSONObject jObject = (JSONObject) jArray.get(0);
        jArray = getLibrary();
        jArray.remove(jObject);

        switch(field.toLowerCase().trim()) {
            case "title":
                jObject.replace("Title", value.trim());
                addJSONBook(jObject, jArray);
                break;
            case "author":
                jObject.replace("Author", value.trim());
                addJSONBook(jObject, jArray);
                break;
            case "quantity":
                jObject.replace("Quantity", value.trim());
                addJSONBook(jObject, jArray);
                break;
            case "price":
                jObject.replace("Price", value.trim());
                addJSONBook(jObject, jArray);
                break;
            case "year":
                jObject.replace("Year", value.trim());
                addJSONBook(jObject, jArray);
                break;
            case "index":
                jObject.replace("Index", value.trim());
                addJSONBook(jObject, jArray );
                break;

        }
    }

    public String calculatePrice(String barcode) throws IOException, ParseException {

        if (getBook(barcode)!=null) {
            Book book = getBook(barcode);
            double price = 0;
            price = book.getQuantity() * book.getUnitPrice();

            if (book.getYear() != null) {
                price = price * (util.getDate() - book.getYear());
                return String.format("%1$,.2f", price);
            } else if (book.getScIndex() != null) {
                price = price * book.getScIndex();
                return String.format("%1$,.2f", price);
            }
            return String.format("%1$,.2f", price);
        } else {
            return null;
        }

    }

    private JSONArray getLibrary () throws IOException, ParseException {
        return (JSONArray) parser.parse(bookRepo.getDatabase());
    }
}
