package pro.sky.webdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.webdemo.model.Book;

import java.util.Collection;

//@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByNameIgnoreCase(String name);

    Collection<Book> findBooksByAuthorContainsIgnoreCase(String author);

    Collection<Book> findAllByNameContainsIgnoreCase(String part);

    Collection<Book> findBooksByNameIgnoreCaseAndAuthor(String name, String author);

    Collection<Book> findBooksByNameOrAuthorAndIdGreaterThan(String name, String author, Long number);
}
