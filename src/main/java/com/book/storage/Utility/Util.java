package com.book.storage.Utility;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Util {

    public boolean validateInt (String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    public boolean validateDouble (String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    public int getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date time = new Date();
        return Integer.parseInt(dateFormat.format(time));
    }
    public void saveFile (String path, String content) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(content);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
