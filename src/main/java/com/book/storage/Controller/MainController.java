package com.book.storage.Controller;

import com.book.storage.Model.Book;
import com.book.storage.Service.BookService;
import com.book.storage.Utility.Util;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    BookService bookService;
    @Autowired
    Util util;
    //Saving path for checking purposes
    private final String getBook = "src/main/resources/static/get_book.txt";
    private final String price = "src/main/resources/static/price.txt";

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("book", new Book());
        return "index";
    }

    //Adding book to the system
    @PostMapping("/add_book")
    public String addBook( @RequestParam String barcode,
                           @RequestParam String name,
                           @RequestParam String author,
                           @RequestParam String quantity,
                           @RequestParam String unitPrice,
                           @RequestParam String year,
                           @RequestParam String scIndex) throws IOException, ParseException {

        if (bookService.getBook(barcode)==null) {
            if (util.validateInt(quantity) && util.validateDouble(unitPrice)) {
                Book book = new Book(barcode, name, author, Integer.parseInt(quantity),
                        Double.parseDouble(unitPrice));

                if (!year.equals("") && util.validateInt(year)) {
                    int date = Integer.parseInt(year);
                    if (date <= 1900) {
                        book.setYear(date);
                    }
                }

                if (!scIndex.equals("") && util.validateInt(scIndex)) {
                    int index = Integer.parseInt(scIndex);
                    if (index >= 1 && index <= 10) {
                        book.setScIndex(Integer.parseInt(scIndex));
                    }
                }
                bookService.addBook(book);

            }
        } else {
            Book book  = bookService.getBook(barcode);
            book.setQuantity(book.getQuantity()+1);
            bookService.setField(barcode, "quantity", String.valueOf(book.getQuantity()));
        }
        return "index";
    }

    //Getting book by barcode
    @GetMapping(value = "/get_book", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getBook(@RequestParam("barcode") String barcode) throws IOException, ParseException {
        String json = bookService.getBookString(barcode);
        //For checking
        util.saveFile(getBook,json);
        return json;

    }

    //Updating information field of book determined by barcode
    @PostMapping("/update")
    @ResponseBody
    public String updateBook(@RequestParam("barcode") String barcode,
                             @RequestParam("field") String field,
                             @RequestParam("value") String value) throws IOException, ParseException {
        if (bookService.getBook(barcode)!=null) {
            bookService.setField(barcode, field, value);
            return "Success";

        } else {
            return "Error";
        }
    }

   //Calculate total price of specific book by barcode
    @GetMapping("/calculate")
    @ResponseBody
    public String calculatePrice(@RequestParam String barcode) throws IOException, ParseException {
        if (bookService.getBook(barcode)!=null) {
            String result = bookService.calculatePrice(barcode);
            //For checking
            util.saveFile(price, barcode+": "+result);
            return result;
        } else {
            return "Error";
        }
    }
}
