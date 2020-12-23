package com.book.storage.Controller;

import com.book.storage.Model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.Scanner;

@Controller
public class MainController {

    private String database = "src/main/resources/static/database";

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("book", new Book());
        return "index";
    }

    @PostMapping("/add_book")
    public String addBook(@RequestParam String barcode,
                          @RequestParam String name,
                          @RequestParam String author,
                          @RequestParam String quantity,
                          @RequestParam String unitPrice,
                          @RequestParam String year,
                          @RequestParam String scIndex) throws IOException {

        Book newBook = new Book(Long.parseLong(barcode), name, author, Integer.parseInt(quantity), Double.parseDouble(unitPrice));
        addToFile(newBook);

        return "index";
    }

    @GetMapping("/get_book")
    public String getBook(@RequestParam String barcode) throws FileNotFoundException {

        return "index";

    }

    public void addToFile(Book book) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(database, true));
        writer.append(String.valueOf(book.getBarcode()))
                .append(" ").append(book.getName())
                .append(" ").append(book.getName())
                .append(" ").append(book.getAuthor())
                .append(" ").append(String.valueOf(book.getQuantity()))
                .append(" ").append(String.valueOf(book.getUnitPrice()))
                .append(" ").append(String.valueOf(book.getScIndex())).append(System.lineSeparator());
        writer.close();
    }

    private String findBook(String barcode) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(database));
        String result = "";
        while (scan.hasNext()) {
            String line = scan.nextLine().toLowerCase().toString();
            if (line.contains(barcode)) {
                System.out.println(line);
                result += line + " ";
            }
        }
        return result;
    }
}
