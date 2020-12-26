package com.book.storage.Controller;

import com.book.storage.Model.Book;
import com.book.storage.Service.BookService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("book", new Book());
        return "index";
    }

    //Adding book to the system
    @PostMapping("/add_book")
    public String addBook(@RequestParam String barcode,
                          @RequestParam String name,
                          @RequestParam String author,
                          @RequestParam String quantity,
                          @RequestParam String unitPrice,
                          @RequestParam String year,
                          @RequestParam String scIndex) throws IOException, ParseException {

        Book book = new Book(barcode, name, author, Integer.parseInt(quantity), Double.parseDouble(unitPrice));

        if (!year.equals("")) {
            int date = Integer.parseInt(year);
            if (date<=1900) {
                book.setYear(Integer.parseInt(year));
            }
        }
        if (scIndex!=null && !scIndex.equals("")) {
            int index = Integer.parseInt(scIndex);
            if (index>=1 && index<=10) {
                book.setScIndex(Integer.parseInt(scIndex));
            }
        }
        bookService.addBook(book);

        return "index";
    }
    //Getting book by barcode
    @RequestMapping(value = "/get_book/{barcode}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getBook(@PathVariable String barcode) throws IOException, ParseException {
        return bookService.getBook(barcode);

    }
    //Updating information field of book determined by barcode
    @PostMapping("/update")
    @ResponseBody
    public String updateBook(@RequestParam("barcode") String barcode,
                             @RequestParam("field") String field,
                             @RequestParam("value") String value) throws IOException, ParseException {
        bookService.setField(barcode, field, value);
        return "index";
    }

   //Calculate total price of specific book by barcode
    @GetMapping("/calculate/{barcode}")
    @ResponseBody
    public String calculatePrice(@PathVariable String barcode) throws IOException, ParseException {
        return bookService.calculatePrice(barcode);
    }
}
