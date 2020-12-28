package com.book.storage.Repository;

import com.book.storage.Model.Book;
import com.book.storage.Utility.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Repository
public class BookRepo {

    private final String database = "src/main/resources/static/database";
    //Saving path for checking purposes
    private final String newBook = "src/main/resources/static/new_book.txt";
    private final String updateBook = "src/main/resources/static/updated_book.txt";

    @Autowired
    ObjectMapper mapper;
    @Autowired
    Util writer;

    //Add Book to database
    public void addBook(Book book) throws IOException, ParseException {

        JSONArray library = new JSONArray();
        long length = new File(database).length();

        if (length != 0 ) {
            JSONParser parser = new JSONParser();
            library = (JSONArray) parser.parse(new FileReader(database));
        }

        library.add(book.toJSONObject());
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(library);
        writer.saveFile(database, json);
        //For checking
        writer.saveFile(newBook, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(library.get(library.size()-1)));

    }

    //Add Book to database when book provided in JSON format
    public void addBookJSON(JSONObject book, JSONArray library) throws IOException, ParseException {


        library.add(book);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(library);
        writer.saveFile(database, json);
        //for checkin
        writer.saveFile(updateBook, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(library.get(library.size()-1)));
    }

    //Get book from database
    public String getBook(String barcode) throws IOException, ParseException {

        String json = getDatabase();

        if (json!=null) {
            Object dataObject = JsonPath.parse(json).read("$[?(@.Barcode == '" + barcode + "')]");
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataObject);
            return json;

        } else {
            return null;

        }
    }

    //Get all database
    public String getDatabase() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        if ((new File(database)).length()>0) {
            return parser.parse(new FileReader(database)).toString();
        } else {
            return null;
        }

    }
}
