package pro.sky.webdemo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.webdemo.model.Book;
import pro.sky.webdemo.model.BookCover;
import pro.sky.webdemo.service.BookCoverService;
import pro.sky.webdemo.service.BookService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("books")
public class BooksController {

    private final BookService bookService;
    private final BookCoverService bookCoverService;

    public BooksController(BookService bookService, BookCoverService bookCoverService) {
        this.bookService = bookService;
        this.bookCoverService = bookCoverService;
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

    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCover(@PathVariable Long id, @RequestParam MultipartFile cover) throws IOException {
        if (cover.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too biig");
        }
        bookCoverService.uploadCover(id, cover);
        return ResponseEntity.ok().build();
    }
    //загрузить обложку - превью из обложки в БД и обложку на локальный диск

    @GetMapping(value = "/{id}/cover/preview")
    public ResponseEntity<byte[]> downloadCover(@PathVariable Long id) {
        BookCover bookCover = bookCoverService.findBookCover(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(bookCover.getMediaType()));
        headers.setContentLength(bookCover.getPreview().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bookCover.getPreview());
    }
    //возвратить превью обложки из БД

    @GetMapping(value = "/{id}/cover")
    public void downloadCover(@PathVariable Long id, HttpServletResponse response) throws IOException {
        BookCover bookCover = bookCoverService.findBookCover(id);
        Path path = Path.of(bookCover.getFilePath());

        try (InputStream is = Files.newInputStream(path);   //данные будут забираться по одному байту без буферизации - для примера
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(bookCover.getMediaType());
            response.setContentLength((int) bookCover.getFileSize());
            is.transferTo(os);
        }
    }
    //возвратить обложку из директории локального диска
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