package com.book.storage.Repository;

import com.book.storage.Model.Book;
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
import java.io.FileWriter;
import java.io.IOException;

@Repository
public class BookRepo {

    private final String database = "src/main/resources/static/database";

    @Autowired
    ObjectMapper mapper;

    public void addBook(Book book) throws IOException, ParseException {

        JSONArray library = new JSONArray();
        long length = new File(database).length();

        if (length != 0 ) {
            JSONParser parser = new JSONParser();
            library = (JSONArray) parser.parse(new FileReader(database));
        }

        library.add(book.toJSONObject());
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(library);

        try {
            FileWriter fileWriter = new FileWriter(database);
                fileWriter.write(json);
                fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addBookJSON(JSONObject book, JSONArray library) throws IOException, ParseException {

        library.add(book);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(library);

        try {
            FileWriter fileWriter = new FileWriter(database);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBook(String barcode) throws IOException, ParseException {

        String json = getDatabase();

        Object dataObject = JsonPath.parse(json).read("$[?(@.Barcode == '"+barcode+"')]");

        json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataObject);

        return json;
    }

    public String getDatabase() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        return parser.parse(new FileReader(database)).toString();
    }
}
