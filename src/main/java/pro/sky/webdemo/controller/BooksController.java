package pro.sky.webdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.webdemo.model.Book;
import pro.sky.webdemo.service.BookService;

import java.util.Collection;

@RestController
@RequestMapping("books")
public class BooksController {

    private BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("{id}")     //GET http://localhost:8080/books/23
    public ResponseEntity<Book> getBookInfo(@PathVariable long id) {
        Book book = bookService.findBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

//    @GetMapping //GET http://localhost:8080/books
//    public ResponseEntity<Collection<Book>> getAllBooks() {
//        return ResponseEntity.ok(bookService.getAllBooks());
//    }

    @GetMapping //GET http://localhost:8080/books
    public ResponseEntity findBooks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String namePart) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(bookService.findByName(name));
        }
        if (author != null && !author.isBlank()) {
            return ResponseEntity.ok(bookService.findBooksByAuthor(author));
        }
        if (namePart != null && !namePart.isBlank()) {
            return ResponseEntity.ok(bookService.findByNamePart(namePart));
        }
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping    //POST http://localhost:8080/books
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping //PUT http://localhost:8080/books
    public ResponseEntity<Book> editBook(@RequestBody Book book) {
        Book foundbook = bookService.editBook(book);
        if (foundbook == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundbook);
    }

    @DeleteMapping("{id}")  //DELETE http://localhost:8080/books/23
    public ResponseEntity deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}


/*
@RestController
@RequestMapping("books")
public class BooksController {

    private BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

//    @GetMapping("{id}")     //GET http://localhost:8080/books/23
//    public Book getBookInfo(@PathVariable long id) {
//        return bookService.findBook(id);
//    }

    @GetMapping("{id}")     //GET http://localhost:8080/books/23
    public ResponseEntity<Book> getBookInfo(@PathVariable long id) {
        Book book = bookService.findBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping //GET http://localhost:8080/books
    public ResponseEntity<Collection<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping    //POST http://localhost:8080/books
    public Book createBook(@RequestBody Book book) { //с этой аннотацией ошибка в сваггере - параметры отправляются в
        // урле, перезагрузил сваггер (обновил страницу) - все заработало
//    public Book createBook(Book book) {
        return bookService.createBook(book);
    }

//    @PutMapping //PUT http://localhost:8080/books
//    public Book editBook(@RequestBody Book book) {
//        return bookService.editBook(book);
//    }

    @PutMapping //PUT http://localhost:8080/books
    public ResponseEntity<Book> editBook(@RequestBody Book book) {
        Book foundbook = bookService.editBook(book);
        if (foundbook == null) {
//            return ResponseEntity.notFound().build();
//            return ResponseEntity.status(400).build();
//            return ResponseEntity.badRequest().build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundbook);
    }

    @DeleteMapping("{id}")  //DELETE http://localhost:8080/books/23
    public Book deleteBook(@PathVariable long id) {
        return bookService.deleteBook(id);
    }
}
*/