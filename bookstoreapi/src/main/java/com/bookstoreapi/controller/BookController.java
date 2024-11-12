package com.bookstoreapi.controller;

import com.bookstoreapi.model.Book;
import com.bookstoreapi.model.MyBook;
import com.bookstoreapi.service.BookService;
import com.bookstoreapi.service.MyBookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Validated
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private MyBookService myBookService;

    /*--------------------------------------------------------------------*/
    /*---------------    A könyvekhez tartozó metódusok    ---------------*/
    /*--------------------------------------------------------------------*/

    //Kezdőlap adatainak megszerzése és átadása a view számára
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listBooks", bookService.getAllBooks());
        return "index";
    }

    //A nem elérhető könyvek adatainak összegyűjtése és átadása a view számára
    @GetMapping("/showBookNotAvailable")
    public String viewBookNotAvailablePage(Model model) {
        List<Book> notAvailable = new ArrayList<>();
        //foreach-el átnézzük a listát
        for(Book book :bookService.getAllBooks()){
            //Ha van benni nem elérhető könyv, azt hozzáadjuk a 'notAvailable' listához
            if(book.getAvailable().equals(false)){ notAvailable.add(book);}
        }
        model.addAttribute("listBooks", notAvailable);
        return "book_not_available";
    }

    //Új könyv hozzáadása nézet
    @GetMapping("/showNewBookForm")
    public String showNewBookForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "new_book";
    }

    //Könyv adatainak mentése adatbázisba validálással
    @PostMapping("/saveBook")
    public String saveBook(@Valid @ModelAttribute("book") Book book, Model model) {
        try {
            bookService.saveBook(book);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "new_book";
        }
    }

    //Szerkesztési nézet megnyitása
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable("id") Long id, Model model) {
        //Szerkeszteni kívánt könyv adatainak átadása a view számára
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "update_book";
    }

    //A könyv szerkesztése
    @PostMapping("/updateBook")
    public String updateBook(@Valid @ModelAttribute("book") Book book, Model model) {
        //Ellenőrzés, aztán ha minden rendben van, mentés az adatbázisba
        try {
            bookService.saveBook(book);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "update_book";
        }
    }

    //Könyv törlése az adatbázisból
    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        this.bookService.deleteBookById(id);
        return "redirect:/";
    }

    /*--------------------------------------------------------------------*/
    /*--------------- A saját könyvekhez tartozó metódusok ---------------*/
    /*--------------------------------------------------------------------*/

    /*MyBook objektum tárolása adatbázisban*/
    @GetMapping("/addToMyBooks/{id}")
    public String addToMyBooks(@PathVariable("id") Long id) {
        //Id ellenőrzése
        int n=0;
        for(MyBook book:myBookService.getAllMyBooks()){
            if(book.getBookId().equals(id))n++;
        }
        //Ha nincs egyező id, akkor mentjük adatbázisba
        if(n==0) {
            MyBook myBook = new MyBook();
            myBook.setBookId(id);
            this.myBookService.saveMyBook(myBook);
        }
        //Visszatérés a kezdőlapra
        return "redirect:/";
    }

    //Könyv törlésa  asaját listából
    @GetMapping("/deleteFromMyBooks/{id}")
    public String deleteFromMyBooks(@PathVariable("id") Long id) {
        List<MyBook> myBooks = myBookService.getAllMyBooks();
        //Végigmegyünk a listán
        for(MyBook myBook:myBooks){
            //Ha található ilyen id, akkor töröljük a saját listánkból
            if(myBook.getBookId().equals(id)){
                this.myBookService.deleteMyBookById(myBook.getId());
                break;
            }
        }
        return "redirect:/showMyBooks";
    }

    //Saját könyvek megjelenítése
    @GetMapping("/showMyBooks")
    public String viewMyBooks(Model model) {
        List<Book> MyBooks = new ArrayList<>();
        //Mivel nem magukat a könyv objektumokat, hanem csak az azonosítújukat mentettük,
        //ezért végigmegyünk mindkét listán és megkeressük a közös elemeket
        for(MyBook myBook :myBookService.getAllMyBooks()){
            for(Book book :bookService.getAllBooks()){
                //A közös elemeket hozzáadjuk a megjelenítendők listájához
                if(myBook.getBookId().equals(book.getId()))
                    MyBooks.add(book);
            }
        }
        //Majd a listát átadjuk a view számára
        model.addAttribute("listBooks", MyBooks);
        return "my_book";
    }
}
